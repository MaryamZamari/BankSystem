package com.javasSE.banking.clientService.clientValidation;

import com.javasSE.banking.clientService.dto.ClientDto;
import com.javasSE.banking.common.validation.IValidation;
import com.javasSE.banking.common.exception.ValidationException;

public class ClientValidation implements IValidation<ClientDto> {
    @Override
    public void validate(ClientDto client) throws ValidationException {
        String name= client.getName();
        boolean hasEmptyName= name == null || name.trim().isEmpty();
        if(hasEmptyName){
            throw new ValidationException("Customer details must not be empty or null!");
        }


    }
}
