package com.javase.banking.accountservice.accountfacade;

import com.javase.banking.accountservice.accountvalidation.AccountValidationContext;
import com.javase.banking.accountservice.dto.AccountDto;
import com.javase.banking.accountservice.exception.DuplicateAccountException;
import com.javase.banking.accountservice.exception.EmptyAccountException;
import com.javase.banking.accountservice.mapper.IAccountMapStruct;
import com.javase.banking.accountservice.model.Account;
import com.javase.banking.accountservice.service.AccountService;
import com.javase.banking.accountservice.service.IAccountService;
import com.javase.banking.accountservice.exception.AccountNotFoundException;
import com.javase.banking.shared.exception.ValidationException;
import org.mapstruct.factory.Mappers;
import java.util.List;

public class AccountFacade implements IAccountFacade {
    private final IAccountService accountService;
    private final AccountValidationContext accountValidation;
    private static final AccountFacade INSTANCE;
    private IAccountMapStruct mapStruct;
    private AccountFacade(){
        accountService= AccountService.getInstance();
        mapStruct= Mappers.getMapper(mapStruct.getClass());
        accountValidation= new AccountValidationContext();
    }
    static{
        INSTANCE= new AccountFacade();
    }
    public static AccountFacade getInstance(){
        return INSTANCE;
    }
    @Override
    public void addAccount(AccountDto accountDto) throws DuplicateAccountException {
        accountService.addAccount(mapStruct.mapToAccount(accountDto));
    }

    @Override
    public AccountDto getAccountById(int id) throws AccountNotFoundException {
        return mapStruct.mapToAccountDto(accountService.getAccountById(id));
    }

    @Override
    public void updateAccount(AccountDto accountDto) throws AccountNotFoundException, ValidationException {
        accountValidation.validate(accountDto);
        int accountId= accountDto.getId();
        Account account= accountService.getAccountById(accountId);
        mapStruct.mapToAccount(accountDto, account);
    }

    @Override
    public void deleteAccount(int id) throws AccountNotFoundException {
        accountService.deleteAccount(id);
    }

    @Override
    public List<AccountDto> getAllActiveAccounts() throws EmptyAccountException {
        return mapStruct.mapToAccountDtoList(accountService.getAllActiveAccounts());
    }

    @Override
    public List<AccountDto> getAllDeletedAccounts() throws EmptyAccountException {
        return mapStruct.mapToAccountDtoList(accountService.getAllDeletedAccounts());
    }


}
