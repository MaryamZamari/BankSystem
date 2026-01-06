package com.javasSE.banking.accountService.service;

import com.javasSE.banking.accountService.exception.AccountNotFoundException;
import com.javasSE.banking.accountService.exception.DuplicateAccountException;
import com.javasSE.banking.common.utility.FileIOUtil;
import com.javasSE.banking.conversionService.utility.ConversionRateCalculatorUtil;
import com.javasSE.banking.conversionService.exception.ConversionNotSupportedException;
import com.javasSE.banking.conversionService.exception.ConversionRateNotFoundException;
import com.javasSE.banking.conversionService.model.*;
import com.javasSE.banking.conversionService.service.ConversionService;
import com.javasSE.banking.conversionService.service.TransactionLogger;
import com.javasSE.banking.accountService.model.Account;
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
    private final FileIOUtil fileIO;
    private static List<Account> accountList;
    private AccountService(){
        this.fileIO = FileIOUtil.getInstance();
        accountList= new ArrayList<>();
        conversionService = ConversionService.getInstance();
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
            fileIO.loadJson("initAccountData");
        } catch(FileNotFoundException ignored){
        }
    }

    @Override
    public void saveOnExit(){
        fileIO.saveJson("initAccountData");
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
            Currency sourceType = sourceAccount.getCurrency();
            Currency destType = destAccount.getCurrency();
            TransactionIdPair idPair = new TransactionIdPair(sourceAccountId , desAccountId);
            CurrencyPair currencyPair = new CurrencyPair(
                    CurrencyType.valueOf(sourceType.getCurrencyCode()) ,
                    CurrencyType.valueOf(destType.getCurrencyCode())
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
