package com.javase.banking.accountservice.facade;

import com.javase.banking.accountservice.exception.TransactionUnsuccessfulException;
import com.javase.banking.accountservice.validation.AccountValidationContext;
import com.javase.banking.accountservice.dto.AccountDto;
import com.javase.banking.accountservice.exception.DuplicateAccountException;
import com.javase.banking.accountservice.exception.EmptyAccountException;
import com.javase.banking.accountservice.mapper.IAccountMapStruct;
import com.javase.banking.accountservice.model.Account;
import com.javase.banking.accountservice.service.AccountService;
import com.javase.banking.accountservice.service.IAccountService;
import com.javase.banking.accountservice.exception.AccountNotFoundException;
import com.javase.banking.clientservice.clientexception.ClientNotFoundException;
import com.javase.banking.clientservice.model.Client;
import com.javase.banking.clientservice.service.ClientService;
import com.javase.banking.clientservice.service.IClientService;
import com.javase.banking.shared.exception.ValidationException;
import com.javase.banking.shared.model.DocFile;
import com.javase.banking.shared.model.FileType;
import org.mapstruct.factory.Mappers;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AccountFacade implements IAccountFacade {
    private final IAccountService accountService;
    private final IClientService clientService;
    private final AccountValidationContext accountValidation;
    private static final AccountFacade INSTANCE;
    private IAccountMapStruct mapStruct;
    private AccountFacade(){
        clientService = ClientService.getInstance();
        accountService= AccountService.getInstance();
        mapStruct= Mappers.getMapper(IAccountMapStruct.class);
        accountValidation= new AccountValidationContext();
    }
    static{
        INSTANCE= new AccountFacade();
    }
    public static AccountFacade getInstance(){
        return INSTANCE;
    }
    @Override
    public void addAccount(AccountDto accountDto) throws DuplicateAccountException, ClientNotFoundException {
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
    public <T> AccountDto getAccountByDetails(T accountDetail) throws AccountNotFoundException {
        return accountService.getAccountByDetail(accountDetail);
    }

    @Override
    public List<AccountDto> getAllActiveAccounts() throws EmptyAccountException {
        return mapStruct.mapToAccountDtoList(accountService.getAllActiveAccounts());
    }

    @Override
    public List<AccountDto> getAllDeletedAccounts() throws EmptyAccountException {
        return mapStruct.mapToAccountDtoList(accountService.getAllDeletedAccounts());
    }

    @Override
    public void initData() {
        //TODO: implement initData in AccoundFacade.
    }

    @Override
    public void saveAccountData(DocFile file) {

    }

    @Override
    public void loadAccountData(FileType type) {

    }

    @Override
    public void saveOnExit() {

    }
    @Override
    public List<AccountDto> searchAccountByClientName(String name) {
        List<Client> clients = clientService.getAllClients();
        List<Account> accountList = new ArrayList<>();
        for (Client client : clients) {
            accountList = accountService.getAccountByClientId(client.getId());
        }
        return mapStruct.mapToAccountDtoList(accountList);
    }
    @Override
    public void deposit(int accountId , BigDecimal amount) throws AccountNotFoundException{
        accountService.deposit(accountId , amount);
    }

    @Override
    public void withdraw(int accountId , BigDecimal amount) throws AccountNotFoundException, ValidationException {
        accountService.withdraw(accountId , amount);
    }

    @Override
    public void transfer(int sourceAccountId, int desAccountId, BigDecimal amount) throws AccountNotFoundException, ValidationException, TransactionUnsuccessfulException {
        try{
            withdraw(sourceAccountId , amount);
            deposit(desAccountId , amount);
        }catch(Exception exception){
            throw new TransactionUnsuccessfulException();
        }
    }


}
