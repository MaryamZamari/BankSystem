package com.javase.banking.facade.validation.clientvalidation;

import com.javase.banking.dto.client.LegalClientDto;
import com.javase.banking.facade.validation.IValidation;
import com.javase.banking.service.exception.ValidationException;

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
