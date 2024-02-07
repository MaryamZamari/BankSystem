package com.javase.banking.service.account;

import com.fasterxml.jackson.core.type.TypeReference;
import com.javase.banking.model.account.Account;
import com.javase.banking.model.client.Client;
import com.javase.banking.service.exception.FileException;
import com.javase.banking.service.exception.accountexception.AccountNotFoundException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AccountService implements IAccountService{
    private static final AccountService INSTANCE;
    private static List<Account> accountList;
    private AccountService(){
        accountList= new ArrayList<>();
    }
    static{
        INSTANCE= new AccountService();
    }
    public static AccountService getInstance(){
        return INSTANCE;
    }

    @Override
    public void addAccount(Account account) {
        accountList.add(account);
    }

    @Override
    public Account getAccount(int id) throws AccountNotFoundException {
        Optional<Account> account= accountList.stream()
                .filter(x -> !x.getDeleted())
                .filter(x -> x.getId().equals(id)).findFirst();
        return account.orElseThrow(() -> new AccountNotFoundException());
    }

    @Override
    public void updateAccount(int id, Account newAccount) throws AccountNotFoundException {
        Optional<Account> account= accountList.stream()
                .filter(x -> !x.getDeleted())
                .filter(x -> x.getId().equals(id)).findFirst();
        account.
    }

    @Override
    public void deleteAccount(int id) throws AccountNotFoundException{
        Account account= getAccount(id);
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
                .filter(x -> x.getDeleted()).collect(Collectors.toList());
    }

    @Override
    public void saveData(File file) {

    }

    @Override
    public void loadData(File file) {

    }

    @Override
    public void addData(String name) throws FileException, FileNotFoundException {
        try{
            accountList= objectMapper.readValue(new File(name + ".jason"),
                    new TypeReference<List<Account>>() { });
        } catch (FileNotFoundException exception){
            throw new FileNotFoundException();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
