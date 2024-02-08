package com.javase.banking.clientservice.clientvalidation;

import com.javase.banking.clientservice.dto.LegalClientDto;
import com.javase.banking.shared.validation.IValidation;
import com.javase.banking.shared.exception.ValidationException;

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
