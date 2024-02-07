package com.javase.banking.facade.validation.clientvalidation;

import com.javase.banking.dto.client.ClientDto;
import com.javase.banking.facade.validation.IValidation;
import com.javase.banking.service.exception.ValidationException;

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
