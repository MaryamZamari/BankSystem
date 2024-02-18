package com.javasSE.banking.accountService.validation;

import com.javasSE.banking.accountService.dto.AccountDto;
import com.javasSE.banking.accountService.facade.AccountFacade;
import com.javasSE.banking.clientService.clientException.ClientNotFoundException;
import com.javasSE.banking.clientService.clientFacade.ClientFacade;
import com.javasSE.banking.common.exception.ValidationException;
import com.javasSE.banking.common.validation.ValidationContext;

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
