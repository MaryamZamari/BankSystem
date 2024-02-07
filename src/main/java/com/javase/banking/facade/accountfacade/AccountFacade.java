package com.javase.banking.facade.accountfacade;

import com.javase.banking.dto.client.account.AccountDto;
import com.javase.banking.model.account.Account;
import com.javase.banking.service.account.AccountService;
import com.javase.banking.service.account.IAccountService;
import com.javase.banking.service.exception.accountexception.AccountNotFoundException;

import java.io.File;
import java.util.List;

public class AccountFacade implements IAccountFacade {
    private final IAccountService accountService;
    private static final AccountFacade INSTANCE;
    private AccountFacade(){
        accountService= AccountService.getInstance();
    }
    static{
        INSTANCE= new AccountFacade();
    }
    public static AccountFacade getInstance(){
        return INSTANCE;
    }
    @Override
    public void addAccount(AccountDto account) {
        accountService.addAccount(account);
    }

    @Override
    public AccountDto getAccount(int id) throws AccountNotFoundException {
        return accountService.getAccount(id);
    }

    @Override
    public void updateAccount(int id) {
        accountService.updateAccount(id);
    }

    @Override
    public void deleteAccount(int id) throws AccountNotFoundException {
        accountService.deleteAccount(id);
    }

    @Override
    public List<AccountDto> getAllActiveAccounts() {
        return accountService.getAllActiveAccounts();
    }

    @Override
    public List<AccountDto> getAllDeletedAccounts() {
        return accountService.getAllDeletedAccounts();
    }

    @Override
    public void saveData(File file) {
        accountService.saveData(file);
    }

    @Override
    public void loadData(File file) {
        accountService.loadData(file);
    }
}
