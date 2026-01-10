package com.javasSE.banking.accountService.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javasSE.banking.accountService.exception.AccountNotFoundException;
import com.javasSE.banking.accountService.exception.DuplicateAccountException;
import com.javasSE.banking.accountService.facade.IAccountFacade;
import com.javasSE.banking.accountService.model.Amount;
import com.javasSE.banking.common.model.DocFile;
import com.javasSE.banking.common.model.FileType;
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
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AccountService implements IAccountService{
    private static final AccountService INSTANCE;
    private final TransactionLogger transactionLogger;
    private final ConversionService conversionService;
    private final ObjectMapper objectMapper;
    private static List<Account> accountList;
    private AccountService(){
        accountList= new ArrayList<>();
        conversionService = ConversionService.getInstance();
        objectMapper= MapperWrapper.getInstance();
        transactionLogger = TransactionLogger.getInstance();
    }
    static{
        INSTANCE= new AccountService();
    }
    public static AccountService getInstance(){
        return INSTANCE;
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
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deposit(int accountId, Amount amountToDeposit) throws AccountNotFoundException {
        Account account = getAccountById(accountId);
        BigDecimal currentBalance = account.getBalance().getValue();
        BigDecimal newBalance = currentBalance.add(amountToDeposit.getValue());
        System.out.println("you deposited " + amountToDeposit + " " + amountToDeposit.getCurrency() + ". \n" +
                "new balance is: " + newBalance);
    }

    @Override
    public void withdraw(int accountId, Amount amountToWithdraw) throws AccountNotFoundException , ValidationException {
        Account account = getAccountById(accountId);
        BigDecimal currentBalance = account.getBalance().getValue();
        boolean hasBalance = hasEnoughBalance(amountToWithdraw , currentBalance);
        BigDecimal newBalance;
        if(hasBalance){
            newBalance = currentBalance.subtract(amountToWithdraw.getValue());
            System.out.println("you withdrawed " + amountToWithdraw + " " + amountToWithdraw.getCurrency() + ". \n" +
                                "new balance is: " + newBalance);
        }else{
            throw new ValidationException("Balance is not enough! try to withdraw less amount. ");
        }
    }

    private static boolean hasEnoughBalance(Amount amountToSubtract, BigDecimal sourceBalance) {
        return (sourceBalance.compareTo(BigDecimal.ZERO) > 0) &&
                (sourceBalance.compareTo(amountToSubtract.getValue()) >= 0);
    }

    @Override
    public void transfer(int sourceAccountId, int desAccountId, Amount amountToTransfer) throws AccountNotFoundException, ValidationException {
        try {
            Account sourceAccount = getAccountById(sourceAccountId);
            Account destAccount = getAccountById(desAccountId);
            Currency sourceType = sourceAccount.getBalance().getCurrency();
            Currency destType = destAccount.getBalance().getCurrency();
            TransactionIdPair idPair = new TransactionIdPair(sourceAccountId , desAccountId);
            CurrencyPair currencyPair = new CurrencyPair(
                    CurrencyType.valueOf(sourceType.getCurrencyCode()) ,
                    CurrencyType.valueOf(destType.getCurrencyCode())
            );
            Transaction transaction = conversionService.createTransaction(idPair , currencyPair , amountToTransfer);
            transactionLogger.logTransaction(transaction);
            if(!sourceType.equals(destType)){
                System.out.println("This operation needs Currency Conversion and will cost some service fee");
                ConversionRate rate = ConversionRateCalculatorUtil.pickConversionRate(currencyPair);
                conversionService.convert(transaction);
            }
            BigDecimal sourceBalance = sourceAccount.getBalance().getValue();
            boolean hasBalance = hasEnoughBalance(amountToTransfer, sourceBalance);
            if (hasBalance) {
                sourceAccount.getBalance().getValue().subtract(amountToTransfer.getValue());
                destAccount.getBalance().getValue().add(amountToTransfer.getValue());
            } else {
                throw new ValidationException("Balance in the source account is not enough");
            }
        }catch (ConversionNotSupportedException e) {
            throw new RuntimeException("Conversion is not possible for the currencies");
        }catch(Exception e){
        } catch (ConversionRateNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

}
