package com.javase.banking.facade.validation.clientvalidation;

import com.javase.banking.dto.client.ClientDto;
import com.javase.banking.facade.validation.IValidation;
import com.javase.banking.service.exception.ValidationException;

public class NumberValidation implements IValidation<ClientDto> {
    @Override //TODO: implement it in the numberService.
    public void validate(ClientDto client) throws ValidationException {
        String number = client.getNumber();
        if(number == null || number.trim().isEmpty()){
            throw new ValidationException("Number can not be empty or null!");
        }
        if(number.matches("^098\\d{10}$|^00\\{12}$|^+\\d{12}$")){
            throw new ValidationException("Invalid number format!");
        }
    }//TODO: Add regex for email and password.



}
