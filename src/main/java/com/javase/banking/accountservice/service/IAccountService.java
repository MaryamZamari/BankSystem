package com.javase.banking.accountservice.service;

import com.javase.banking.accountservice.exception.DuplicateAccountException;
import com.javase.banking.accountservice.exception.EmptyAccountException;
import com.javase.banking.accountservice.model.Account;
import com.javase.banking.shared.exception.FileException;
import com.javase.banking.accountservice.exception.AccountNotFoundException;
import com.javase.banking.shared.model.DocFile;
import java.io.FileNotFoundException;
import java.util.List;

public interface IAccountService {
    void addAccount(Account account) throws DuplicateAccountException;
    Account getAccountById(int id) throws AccountNotFoundException;
    void updateAccount(int id, Account newAccount) throws AccountNotFoundException;
    void deleteAccount(int id) throws AccountNotFoundException;
    List<Account> getAllActiveAccounts() throws EmptyAccountException;
    List<Account> getAllDeletedAccounts() throws EmptyAccountException;
    void initData();
    void saveOnExit();
    void addData(String name) throws FileException, FileNotFoundException;
    void loadData(DocFile file) throws FileNotFoundException;
}
