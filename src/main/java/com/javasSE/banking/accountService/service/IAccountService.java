package com.javasSE.banking.accountService.service;

import com.javasSE.banking.accountService.dto.AccountDto;
import com.javasSE.banking.accountService.exception.AccountNotFoundException;
import com.javasSE.banking.accountService.exception.DuplicateAccountException;
import com.javasSE.banking.accountService.exception.EmptyAccountException;
import com.javasSE.banking.clientService.clientException.ClientNotFoundException;
import com.javasSE.banking.common.model.DocFile;
import com.javasSE.banking.accountService.model.Account;
import com.javasSE.banking.common.exception.FileException;
import com.javasSE.banking.common.exception.ValidationException;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.List;

public interface IAccountService {
    void addAccount(Account account) throws DuplicateAccountException, ClientNotFoundException;
    Account getAccountById(int id) throws AccountNotFoundException;
    void updateAccount(int id, Account newAccount) throws AccountNotFoundException;
    void deleteAccount(int id) throws AccountNotFoundException;
    List<Account> getAllActiveAccounts() throws EmptyAccountException;
    List<Account> getAllDeletedAccounts() throws EmptyAccountException;
    void initData();
    void saveOnExit();
    void addData(String name) throws FileException, FileNotFoundException;
    void loadData(DocFile file) throws FileNotFoundException;
    <T> AccountDto getAccountByDetail(T accountDetail) throws AccountNotFoundException;
    List<Account> getAccountByClientId(Integer id) ;
    void deposit(int accountId, BigDecimal amount) throws AccountNotFoundException;
    void withdraw(int accountId, BigDecimal amount) throws AccountNotFoundException, ValidationException;
    void transfer(int sourceAccountId, int desAccountId, BigDecimal amount) throws AccountNotFoundException, ValidationException;
}
