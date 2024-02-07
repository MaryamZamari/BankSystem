package com.javase.banking.service.account;

import com.javase.banking.model.account.Account;
import com.javase.banking.service.exception.FileException;
import com.javase.banking.service.exception.accountexception.AccountNotFoundException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public interface IAccountService {
    void addAccount(Account account);
    Account getAccount(int id) throws AccountNotFoundException;
    void updateAccount(int id, Account newAccount) throws AccountNotFoundException;
    void deleteAccount(int id) throws AccountNotFoundException;
    List<Account> getAllActiveAccounts();
    List<Account> getAllDeletedAccounts();
    void saveData(File file) throws FileException;
    void loadData(File file) throws FileException;
    void addData(String name) throws FileException, FileNotFoundException;

}
