package com.javase.banking.facade.validation.clientvalidation;

import com.javase.banking.dto.client.PersonalClientDto;
import com.javase.banking.facade.validation.IValidation;
import com.javase.banking.service.exception.ValidationException;

public class PersonalClientValidation implements IValidation<PersonalClientDto> {
    @Override
    public void validate(PersonalClientDto client) throws ValidationException {
        String family =  client.getSurname();
        boolean hasEmptyName= family == null || family.trim().isEmpty();
        if(hasEmptyName) {
            throw new ValidationException("Customer must not be empty or null!");
        }
    }
}
