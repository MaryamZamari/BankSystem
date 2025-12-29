package com.javasSE.banking.clientService.clientValidation;

import com.javasSE.banking.clientService.dto.ClientDto;
import com.javasSE.banking.common.exception.ValidationException;
import com.javasSE.banking.common.validation.IValidation;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidation implements IValidation<ClientDto> {

    @Override
    public void validate(ClientDto clientDto) throws ValidationException {
        String email = clientDto.getEmail();
        boolean isEmailNullEmpty = clientDto.getEmail() == null || clientDto.getEmail().trim().isEmpty();

        if(isEmailNullEmpty){
            throw new ValidationException("Email can not be null or empty!");
        }
        if(!isCorrectFormat(email)){
            throw new ValidationException("Enter an email with the correct format : email@domain.com");
        }
    }

    private boolean isCorrectFormat(String email){
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$|\"^(?=.{1,64}@)[A-Za-z0-9\\\\+_-]+(\\\\.[A-Za-z0-9\\\\+_-]+)*@\" \n" +
                                                "+ \"[^-][A-Za-z0-9\\\\+-]+(\\\\.[A-Za-z0-9\\\\+-]+)*(\\\\.[A-Za-z]{2,})$\"");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
