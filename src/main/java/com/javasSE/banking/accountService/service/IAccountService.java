package com.javasSE.banking.accountService.service;

import com.javasSE.banking.accountService.dto.AccountDto;
import com.javasSE.banking.accountService.exception.AccountNotFoundException;
import com.javasSE.banking.accountService.exception.DuplicateAccountException;
import com.javasSE.banking.accountService.exception.EmptyAccountException;
import com.javasSE.banking.accountService.model.Amount;
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
    Account getAccountById(Integer id) throws AccountNotFoundException;
    void deleteAccount(int id) throws AccountNotFoundException;
    List<Account> getAllActiveAccounts() throws EmptyAccountException;
    List<Account> getAllDeletedAccounts() throws EmptyAccountException;
    void initData();
    void saveOnExit();
    void addData(String name) throws FileException, FileNotFoundException;
    void loadData(DocFile file) throws FileNotFoundException;
    List<Account> getAccountByClientId(Integer id) ;
    void deposit(int accountId, Amount amount) throws AccountNotFoundException;
    void withdraw(int accountId, Amount amount) throws AccountNotFoundException, ValidationException;
    void transfer(int sourceAccountId, int desAccountId, Amount amount) throws AccountNotFoundException, ValidationException;
}
