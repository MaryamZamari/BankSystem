package com.javasSE.banking.accountService.facade;

import com.javasSE.banking.accountService.dto.AccountDto;
import com.javasSE.banking.accountService.exception.AccountNotFoundException;
import com.javasSE.banking.accountService.exception.TransactionUnsuccessfulException;
import com.javasSE.banking.clientService.clientException.ClientNotFoundException;
import com.javasSE.banking.common.model.DocFile;
import com.javasSE.banking.common.model.FileType;
import com.javasSE.banking.accountService.exception.DuplicateAccountException;
import com.javasSE.banking.accountService.exception.EmptyAccountException;
import com.javasSE.banking.common.exception.ValidationException;

import java.math.BigDecimal;
import java.util.List;

public interface IAccountFacade {
    void addAccount(AccountDto account) throws DuplicateAccountException, ClientNotFoundException;
    AccountDto getAccountById(int id) throws AccountNotFoundException;
    void updateAccount(AccountDto updatedDto) throws AccountNotFoundException, ValidationException;
    void deleteAccount(int id) throws AccountNotFoundException;
    <T> AccountDto getAccountByDetails(T accountDetail) throws AccountNotFoundException;
    List<AccountDto> getAllActiveAccounts() throws EmptyAccountException;
    List<AccountDto> getAllDeletedAccounts() throws EmptyAccountException;
    List<AccountDto> searchAccountByClientName(String name) throws AccountNotFoundException;
    void initData();
    void saveOnExit();
    void saveAccountData(DocFile file);
    void loadAccountData(FileType type);
    void deposit(int accountId, BigDecimal amount) throws AccountNotFoundException;
    void withdraw(int accountId , BigDecimal amount) throws AccountNotFoundException, ValidationException;
    void transfer(int sourceAccountId , int desAccountId, BigDecimal amount) throws AccountNotFoundException, ValidationException, TransactionUnsuccessfulException;


}
