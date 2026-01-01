package com.javasSE.banking.accountService.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javasSE.banking.accountService.dto.AccountDto;
import com.javasSE.banking.accountService.exception.AccountNotFoundException;
import com.javasSE.banking.accountService.exception.DuplicateAccountException;
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
import com.javasSE.banking.accountService.model.AccountType;
import com.javasSE.banking.common.exception.FileException;
import com.javasSE.banking.common.exception.ValidationException;
import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
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
                " account added with these informations: \n" +
                account.toString()); //TODO: the information gets collected from the user correctly, but not added correctly.
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
        Optional<Account> account= accountList.stream()
                .filter(x -> !x.getDeleted())
                .filter(x -> x.getId().equals(id)).findFirst();
        if(!account.isEmpty()) {
            System.out.println("The searched account is: " + account.toString());
        }
        return account.orElseThrow(AccountNotFoundException::new);
    }

    @Override
    public void updateAccount(int accountId, Account newAccount) throws AccountNotFoundException {
        Optional<Account> account = accountList.stream()
                .filter(x -> !x.getDeleted())
                .filter(x -> x.getId().equals(accountId)).findFirst();
        if(account.isEmpty()){
            throw new AccountNotFoundException();
        }
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
    public void deposit(int accountId, BigDecimal amount) throws AccountNotFoundException {
        Account account = getAccountById(accountId);
        account.getBalance().add(amount);
    }

    @Override
    public void withdraw(int accountId, BigDecimal amount) throws AccountNotFoundException , ValidationException {
        Account account = getAccountById(accountId);
        BigDecimal balance = account.getBalance();
        boolean hasBalance = hasEnoughBalance(amount, balance);
        if(hasBalance){
            account.getBalance().subtract(amount);
        }else{
            throw new ValidationException("Balance is not enough");
        }
    }

    private static boolean hasEnoughBalance(BigDecimal amount, BigDecimal balance) {
        return (balance.compareTo(BigDecimal.ZERO) > 0) &&
                (balance.compareTo(amount) >= 0);
    }

    @Override
    public void transfer(int sourceAccountId, int desAccountId, BigDecimal amount) throws AccountNotFoundException, ValidationException {
        try {
            Account sourceAccount = getAccountById(sourceAccountId);
            Account destAccount = getAccountById(desAccountId);
            AccountType sourceType = sourceAccount.getCurrency();
            AccountType destType = destAccount.getCurrency();
            TransactionIdPair idPair = new TransactionIdPair(sourceAccountId , desAccountId);
            CurrencyPair currencyPair = new CurrencyPair(
                    CurrencyType.valueOf(sourceType.name()) ,
                    CurrencyType.valueOf(destType.name())
            );
            Transaction transaction = conversionService.createTransaction(idPair , currencyPair , amount);
            transactionLogger.logTransaction(transaction);
            if(!sourceType.equals(destType)){
                System.out.println("This operation needs Currency Conversion and will cost some service fee");
                ConversionRate rate = ConversionRateCalculatorUtil.pickConversionRate(currencyPair);
                conversionService.convert(transaction);
            }
            BigDecimal sourceBalance = sourceAccount.getBalance();
            boolean hasBalance = hasEnoughBalance(amount, sourceBalance);
            if (hasBalance) {
                sourceAccount.getBalance().subtract(amount);
                destAccount.getBalance().add(amount);
            } else {
                throw new ValidationException("Balance is not enough");
            }
        }catch (ConversionNotSupportedException e) {
            throw new RuntimeException("Conversion is not possible for the currencies");
        }catch(Exception e){
        } catch (ConversionRateNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

}
