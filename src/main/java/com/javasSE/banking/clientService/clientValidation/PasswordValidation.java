package com.javasSE.banking.clientService.clientValidation;

import com.javasSE.banking.clientService.dto.ClientDto;
import com.javasSE.banking.common.exception.ValidationException;
import com.javasSE.banking.common.validation.IValidation;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidation implements IValidation<ClientDto> {
    @Override
    public void validate(ClientDto clientDto) throws ValidationException {
        String password = clientDto.getPassword();
        boolean hasCorrectLength = password.length() >= 8;
        if(!hasCorrectLength){
            throw new ValidationException("Password must have at least 8 characters!");
        }
        Pattern pattern = Pattern
                .compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
        Matcher matcher = pattern.matcher(password);
        boolean isCorrectFormat = matcher.matches();
        if(!isCorrectFormat){
            throw new ValidationException("The password does not have " +
                    "the correct format: length of at least 8 characters," +
                    "at least 1 uppercase ,1 lowercase" +
                    "1 number and 1 special character.");
        }
    }
}
