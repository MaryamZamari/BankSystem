package com.javasSE.banking.clientService.clientValidation;

import com.javasSE.banking.clientService.dto.LegalClientDto;
import com.javasSE.banking.common.validation.IValidation;
import com.javasSE.banking.common.exception.ValidationException;

public class LegalClientValidation implements IValidation<LegalClientDto> {
    @Override
    public void validate(LegalClientDto client) throws ValidationException {
        String personName =  client.getContactPerson();
        boolean hasEmptyName = personName == null || personName.trim().isEmpty();
        if (hasEmptyName) {
            throw new ValidationException("Customer must not be empty or null!");
        }
    }
}
