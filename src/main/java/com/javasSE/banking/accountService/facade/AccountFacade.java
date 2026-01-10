package com.javasSE.banking.accountService.facade;

import com.javasSE.banking.accountService.dto.AccountDto;
import com.javasSE.banking.accountService.dto.AmountDto;
import com.javasSE.banking.accountService.exception.AccountNotFoundException;
import com.javasSE.banking.accountService.exception.TransactionUnsuccessfulException;
import com.javasSE.banking.accountService.mapper.IAccountMapStruct;
import com.javasSE.banking.accountService.mapper.IAmountMapStruct;
import com.javasSE.banking.accountService.validation.AccountValidationContext;
import com.javasSE.banking.clientService.clientException.ClientNotFoundException;
import com.javasSE.banking.common.model.DocFile;
import com.javasSE.banking.common.model.FileType;
import com.javasSE.banking.accountService.exception.DuplicateAccountException;
import com.javasSE.banking.accountService.exception.EmptyAccountException;
import com.javasSE.banking.accountService.model.Account;
import com.javasSE.banking.accountService.service.AccountService;
import com.javasSE.banking.accountService.service.IAccountService;
import com.javasSE.banking.clientService.model.Client;
import com.javasSE.banking.clientService.service.ClientService;
import com.javasSE.banking.clientService.service.IClientService;
import com.javasSE.banking.common.exception.ValidationException;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

public class AccountFacade implements IAccountFacade {
    private final IAccountService accountService;
    private final IClientService clientService;
    private final AccountValidationContext accountValidation;
    private static final AccountFacade INSTANCE;
    private IAccountMapStruct accountMapStruct;
    private IAmountMapStruct amountMapStruct;

    private AccountFacade(){
        clientService = ClientService.getInstance();
        accountService= AccountService.getInstance();
        accountMapStruct = Mappers.getMapper(IAccountMapStruct.class);
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
        accountService.addAccount(accountMapStruct.mapToAccount(accountDto));
    }

    @Override
    public AccountDto getAccountById(int id) throws AccountNotFoundException {
        return accountMapStruct.mapToAccountDto(accountService.getAccountById(id));
    }

    @Override
    public Account updateAccount(int accountId , AccountDto accountDto) throws AccountNotFoundException, ValidationException {
        accountValidation.validate(accountDto);
        Account account= accountService.getAccountById(accountId);
        accountMapStruct.mapToAccountForUpdate(accountDto, account);
        return account;
    }

    @Override
    public void deleteAccount(int id) throws AccountNotFoundException {
        accountService.deleteAccount(id);
    }

    @Override
    public List<AccountDto> getAllActiveAccounts() throws EmptyAccountException {
        return accountMapStruct.mapToAccountDtoList(accountService.getAllActiveAccounts());
    }

    @Override
    public List<AccountDto> getAllDeletedAccounts() throws EmptyAccountException {
        return accountMapStruct.mapToAccountDtoList(accountService.getAllDeletedAccounts());
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
        return accountMapStruct.mapToAccountDtoList(accountList);
    }
    @Override
    public void deposit(int accountId , AmountDto amountDto) throws AccountNotFoundException{
        accountService.deposit(accountId , amountMapStruct.mapToAmount(amountDto));
    }

    @Override
    public void withdraw(int accountId , AmountDto amountDto) throws AccountNotFoundException, ValidationException {
        accountService.withdraw(accountId , amountMapStruct.mapToAmount(amountDto));
    }

    @Override
    public void transfer(int sourceAccountId, int desAccountId, AmountDto amountDto) throws AccountNotFoundException, ValidationException, TransactionUnsuccessfulException {
        try{
            accountService.transfer(sourceAccountId, desAccountId, amountMapStruct.mapToAmount(amountDto));
        }catch(Exception exception){
            throw new TransactionUnsuccessfulException();
        }
    }


}
