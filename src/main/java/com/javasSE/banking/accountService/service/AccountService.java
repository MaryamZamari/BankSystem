package com.javasSE.banking.accountService.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javasSE.banking.accountService.exception.AccountNotFoundException;
import com.javasSE.banking.accountService.exception.DuplicateAccountException;
import com.javasSE.banking.accountService.facade.IAccountFacade;
import com.javasSE.banking.accountService.model.Amount;
import com.javasSE.banking.common.model.DocFile;
import com.javasSE.banking.common.model.FileType;
import com.javasSE.banking.common.utility.AmountUtil;
import com.javasSE.banking.common.utility.MapperWrapper;
import com.javasSE.banking.conversionService.utility.ConversionRateCalculatorUtil;
import com.javasSE.banking.conversionService.exception.ConversionNotSupportedException;
import com.javasSE.banking.conversionService.exception.ConversionRateNotFoundException;
import com.javasSE.banking.conversionService.model.*;
import com.javasSE.banking.conversionService.service.ConversionService;
import com.javasSE.banking.conversionService.service.TransactionLogger;
import com.javasSE.banking.accountService.model.Account;
import com.javasSE.banking.common.exception.FileException;
import com.javasSE.banking.common.exception.ValidationException;
import java.io.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class AccountService implements IAccountService{
    private static final AccountService INSTANCE;
    private final TransactionLogger transactionLogger;
    private final ConversionService conversionService;
    private final ObjectMapper objectMapper;
    private final AmountUtil amountUtil;
    private static List<Account> accountList;
    private Map<Integer , Lock> locks = new HashMap<>(); //to handle concurrency java 5

    private AccountService(){
        accountList= new ArrayList<>();
        conversionService = ConversionService.getInstance();
        objectMapper= MapperWrapper.getInstance();
        transactionLogger = TransactionLogger.getInstance();
        amountUtil = AmountUtil.getInstance();
    }
    static{
        INSTANCE= new AccountService();
    }
    public static AccountService getInstance(){
        return INSTANCE;
    }

    private Lock getLock(int accountId){
        locks.putIfAbsent(accountId , new ReentrantLock());
        return locks.get(accountId);
    }

    @Override
    public void addAccount(Account account) throws DuplicateAccountException {
        accountList.add(account);
        System.out.println("Account id: " + account.getId() +
                ". account added with these informations: \n" +
                account.toString());
    }


    @Override
    public List<Account> getAccountByClientId(Integer id){
        return accountList.stream()
                .filter(account -> !account.getDeleted())
                .filter(account -> account.getClientId().equals(id))
                .collect(Collectors.toList());
    }

    @Override
    public Account getAccountById(Integer id) throws AccountNotFoundException {
        Optional<Account> account = getAccount(id);
        if(!account.isEmpty()) {
            System.out.println("The searched account is: " + account.toString());
        }
        return account.orElseThrow(AccountNotFoundException::new);
    }

    private static Optional<Account> getAccount(Integer id) {
        Optional<Account> account= accountList.stream()
                .filter(x -> !x.getDeleted())
                .filter(x -> x.getId().equals(id)).findFirst();
        return account;
    }


    @Override
    public void deleteAccount(int id) throws AccountNotFoundException{
        Account account= getAccountById(id);
        account.setDeleted(true);
    }

    @Override
    public List<Account> getAllActiveAccounts() {
        return accountList.stream()
                .filter(x -> !x.getDeleted()).collect(Collectors.toList());
    }

    @Override
    public List<Account> getAllDeletedAccounts() {
        return accountList.stream()
                .filter(Account::getDeleted).collect(Collectors.toList());
    }



    @Override
    public void deposit(int accountId, Amount amountToDeposit) throws AccountNotFoundException {
        Account account = getAccountById(accountId);
        Lock lock = getLock(accountId);
        lock.lock();
        //synchronized (account){  --> to handle concurrency problem
        try{
            Amount currentBalance = new Amount(account.getBalance().getCurrency() , account.getBalance().getValue());
            Amount newBalance = AmountUtil.add(currentBalance , amountToDeposit);
            account.setBalance(newBalance);
            System.out.println("you deposited " + amountToDeposit + " " + amountToDeposit.getCurrency() + ". \n" +
                    "new balance is: " + newBalance);
        }finally{
            lock.unlock();
        }
           // }
            }



    @Override
    public void withdraw(int accountId, Amount amountToWithdraw) throws AccountNotFoundException , ValidationException {
        Account account = getAccountById(accountId);
        Lock lock = getLock(accountId);
        lock.lock();
        //synchronized (account){
        try{
            Amount currentBalance = new Amount(account.getBalance().getCurrency() , account.getBalance().getValue());
            boolean hasBalance = hasEnoughBalance(amountToWithdraw , currentBalance);
            if(hasBalance){
                Amount newBalance = AmountUtil.subtract(currentBalance , amountToWithdraw);
                System.out.println("you withdrawed " + amountToWithdraw + " " + amountToWithdraw.getCurrency() + ". \n" +
                        "new balance is: " + newBalance);
                lock.unlock();
            }else{
                throw new ValidationException("Balance is not enough! try to withdraw less amount. ");
        }

        }finally {
            lock.unlock(); //to avoid deadlock if the program crashes in the middle of the operation
        }
    }

    private static boolean hasEnoughBalance(Amount sourceAmount , Amount amountToSubtract) {
        return AmountUtil.compareTo(sourceAmount , amountToSubtract);
    }

    @Override
    public void transfer(int sourceAccountId, int destAccountId, Amount amountToTransfer) throws AccountNotFoundException, ValidationException {
        Account sourceAccount = getAccountById(sourceAccountId); //TODO: review this method and fix
        Account destAccount = getAccountById(destAccountId);
        //solving deadlock problem : locking in order of the id
            //Account firstAccountLock = sourceAccountId < destAccountId ? sourceAccount : destAccount;
            //Account secondAccountLock = sourceAccountId < destAccountId ? destAccount : sourceAccount;
        Lock sourceLock = getLock(sourceAccountId);
        Lock destLock = getLock(destAccountId);

        Lock firstAccountLock = sourceAccountId < destAccountId ? sourceLock : destLock;
        Lock secondAccountLock = sourceAccountId < destAccountId ? destLock : sourceLock;

        firstAccountLock.lock();
        secondAccountLock.lock();

            //synchronized (firstAccountLock){
               // synchronized (secondAccountLock){
        try {
            Currency sourceType = sourceAccount.getBalance().getCurrency();
            Currency destType = destAccount.getBalance().getCurrency();
            TransactionIdPair idPair = new TransactionIdPair(sourceAccountId , destAccountId);
            CurrencyPair currencyPair = new CurrencyPair(
                    CurrencyType.valueOf(sourceType.getCurrencyCode()) ,
                    CurrencyType.valueOf(destType.getCurrencyCode())
            );
            Transaction transaction = conversionService.createTransaction(idPair , currencyPair , amountToTransfer);
            transactionLogger.logTransaction(transaction);
            if(!sourceType.equals(destType)){
                System.out.println("This operation needs Currency Conversion and will cost some service fee /n");
                BigDecimal convertedAmount = conversionService.convert(transaction);
                System.out.println("the converted amount is : " + convertedAmount + " " + destType.getDisplayName() + "/n");
            }

            Amount sourceBalance = new Amount(getAccountById(sourceAccountId).getBalance().getCurrency() , sourceAccount.getBalance().getValue());
            boolean hasBalance = hasEnoughBalance(sourceBalance, amountToTransfer);
            if (hasBalance) {
                AmountUtil.subtract(sourceBalance , amountToTransfer);
                AmountUtil.add(getAccountById(sourceAccountId).getBalance() , amountToTransfer);
            } else {
                throw new ValidationException("Balance in the source account is not enough");
            }
        }catch (ConversionNotSupportedException e) {
            throw new RuntimeException("Conversion is not possible for the currencies");
        }catch (ConversionRateNotFoundException e) {
            throw new RuntimeException(e);
        }finally{
            firstAccountLock.unlock();
            secondAccountLock.unlock();
        }
    }


    // =========== Data and file related functions
    @Override
    public void initData() {
        try{
            loadJson("initAccountData");
        } catch(FileNotFoundException ignored){
        }
    }

    @Override
    public void saveOnExit(){
        saveJson("initAccountData");
    }

    @Override
    public void addData(String fileName) throws FileNotFoundException {
        try{
            accountList = objectMapper.readValue(new File(fileName + ".jason"),
                    new TypeReference<List<Account>>() { });
        } catch (FileNotFoundException exception){
            throw new FileNotFoundException();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveJson(String fileName) {
        try{
            File file= new File(fileName + ".json");
            if(!file.exists()){
                file.createNewFile();
            }
            objectMapper.writeValue(file, accountList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveSerialised(String fileName) throws FileException {
        try{
            File file= new File(fileName + ".crm");
            if(!file.exists()){
                file.createNewFile();
            }
            try(FileOutputStream fileOutputStream = new FileOutputStream(file);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);){
                objectOutputStream.writeObject(accountList);
            }
        }catch(IOException exception){
            throw new FileException();
        }
    }

    @Override
    public void loadData(DocFile file) throws FileNotFoundException {
        FileType type= file.getType();
        String fileName= file.getName();
        switch (type){
            case SERIALISED -> loadSerialised(fileName);
            case JSON -> loadJson(fileName);
        }
    }

    private void loadJson(String fileName) throws FileNotFoundException {
        try{
            accountList = objectMapper.readValue(new File(fileName + ".jason"),
                    new TypeReference<List<Account>>() { }); //to give it a more specific object
        } catch (FileNotFoundException exception){
            throw new FileNotFoundException();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadSerialised(String fileName) throws FileNotFoundException {
        try(FileInputStream fileInputStream = new FileInputStream(fileName)){
            ObjectInputStream objectInputStream= new ObjectInputStream(fileInputStream);
            accountList = (List<Account>) objectInputStream.readObject();
        } catch (FileNotFoundException exception){
            throw new FileNotFoundException();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
