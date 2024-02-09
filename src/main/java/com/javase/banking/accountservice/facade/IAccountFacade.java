package com.javase.banking.accountservice.facade;

import com.javase.banking.accountservice.dto.AccountDto;
import com.javase.banking.accountservice.exception.DuplicateAccountException;
import com.javase.banking.accountservice.exception.EmptyAccountException;
import com.javase.banking.accountservice.exception.AccountNotFoundException;
import com.javase.banking.accountservice.exception.TransactionUnsuccessfulException;
import com.javase.banking.clientservice.clientexception.ClientNotFoundException;
import com.javase.banking.shared.exception.ValidationException;
import com.javase.banking.shared.model.DocFile;
import com.javase.banking.shared.model.FileType;
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
