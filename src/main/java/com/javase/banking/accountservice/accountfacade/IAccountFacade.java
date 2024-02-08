package com.javase.banking.accountservice.accountfacade;

import com.javase.banking.accountservice.dto.AccountDto;
import com.javase.banking.accountservice.exception.DuplicateAccountException;
import com.javase.banking.accountservice.exception.EmptyAccountException;
import com.javase.banking.accountservice.exception.AccountNotFoundException;
import com.javase.banking.shared.exception.ValidationException;

import java.util.List;

public interface IAccountFacade {
    void addAccount(AccountDto account) throws DuplicateAccountException;
    AccountDto getAccountById(int id) throws AccountNotFoundException;
    void updateAccount(AccountDto accountDto) throws AccountNotFoundException, ValidationException;
    void deleteAccount(int id) throws AccountNotFoundException;
    List<AccountDto> getAllActiveAccounts() throws EmptyAccountException;
    List<AccountDto> getAllDeletedAccounts() throws EmptyAccountException;

}
