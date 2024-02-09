package com.javase.banking.accountservice.validation;

import com.javase.banking.accountservice.dto.AccountDto;
import com.javase.banking.accountservice.facade.AccountFacade;
import com.javase.banking.clientservice.clientexception.ClientNotFoundException;
import com.javase.banking.clientservice.clientfacade.ClientFacade;
import com.javase.banking.shared.exception.ValidationException;
import com.javase.banking.shared.validation.ValidationContext;

public class AccountValidationContext extends ValidationContext<AccountDto> {
    private final AccountFacade accountFacade;
    private final ClientFacade clientFacade;

    public AccountValidationContext(){
        accountFacade= AccountFacade.getInstance();
        clientFacade= ClientFacade.getInstance();
        new BalanceValidation();
        addValidationItem(accountDto -> {
            try {
                Integer clientId = accountDto.getClientId();
                clientFacade.getClientById(clientId);
            }catch(ClientNotFoundException exception){
                throw new ValidationException("Client id is not valid!");
            }
        });


    }




}
