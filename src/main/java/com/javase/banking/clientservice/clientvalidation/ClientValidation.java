package com.javase.banking.clientservice.clientvalidation;

import com.javase.banking.clientservice.dto.ClientDto;
import com.javase.banking.shared.validation.IValidation;
import com.javase.banking.shared.exception.ValidationException;

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
