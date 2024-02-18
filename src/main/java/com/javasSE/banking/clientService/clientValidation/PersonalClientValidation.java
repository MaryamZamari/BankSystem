package com.javasSE.banking.clientService.clientValidation;

import com.javasSE.banking.clientService.dto.PersonalClientDto;
import com.javasSE.banking.common.validation.IValidation;
import com.javasSE.banking.common.exception.ValidationException;

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
