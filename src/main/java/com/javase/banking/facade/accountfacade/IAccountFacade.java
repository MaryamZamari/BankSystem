package com.javase.banking.facade.accountfacade;

import com.javase.banking.dto.client.account.AccountDto;
import com.javase.banking.model.account.Account;
import com.javase.banking.service.exception.accountexception.AccountNotFoundException;

import java.io.File;
import java.util.List;

public interface IAccountFacade {
    void addAccount(Account account);
    AccountDto getAccount(int id) throws AccountNotFoundException;
    void updateAccount(int id);
    void deleteAccount(int id) throws AccountNotFoundException;
    List<AccountDto> getAllActiveAccounts();
    List<AccountDto> getAllDeletedAccounts();
    void saveData(File file);
    void loadData(File file);
}
