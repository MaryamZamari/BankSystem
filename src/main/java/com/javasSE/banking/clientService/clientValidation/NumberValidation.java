package com.javasSE.banking.clientService.clientValidation;

import com.javasSE.banking.clientService.dto.ClientDto;
import com.javasSE.banking.common.validation.IValidation;
import com.javasSE.banking.common.exception.ValidationException;

public class NumberValidation implements IValidation<ClientDto> {
    @Override
    public void validate(ClientDto client) throws ValidationException {
        String number = client.getNumber();
        if(number == null || number.trim().isEmpty()){
            throw new ValidationException("Number can not be empty or null!");
        }
        if(!isCorrectFormat(number)){
            throw new ValidationException("Invalid number format!");
        }
    }

    private static boolean isCorrectFormat(String number) {
        return number.matches("^098\\d{10}$|^00\\{12}$|^+\\d{12}$");
    }


}
