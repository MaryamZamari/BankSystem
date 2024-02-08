package com.javase.banking.clientservice.clientvalidation;

import com.javase.banking.clientservice.dto.PersonalClientDto;
import com.javase.banking.shared.validation.IValidation;
import com.javase.banking.shared.exception.ValidationException;

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
