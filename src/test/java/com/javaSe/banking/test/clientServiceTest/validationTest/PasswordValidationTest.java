package com.javaSe.banking.test.clientServiceTest.validationTest;

import com.javasSE.banking.clientService.clientValidation.PasswordValidation;
import com.javasSE.banking.clientService.dto.ClientDto;
import com.javasSE.banking.clientService.dto.PersonalClientDto;
import com.javasSE.banking.clientService.model.ClientType;
import com.javasSE.banking.common.exception.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.Date;

public class PasswordValidationTest {
    @Test
    public void passwordLengthValidationTest(){
        Date birthDate = new Date("04-Apr-1990");
        ClientDto clientDto = new PersonalClientDto(0 , "name" , "surname" , birthDate ,
                "iranian" , "M1234RGT78B" , "email@gmail.com" ,"To, Street" ,
                ClientType.P , "0123456789" ,  "wrong");
        PasswordValidation validation = new PasswordValidation();
        Assertions.assertThrows(ValidationException.class , () -> {
            validation.validate(clientDto);
        });
    }

    @Test
    public void passwordFormatValidationTest(){
        Date birthDate = new Date("04-Apr-1990");
        ClientDto clientDto = new PersonalClientDto(0 , "name" , "surname" , birthDate ,
                "iranian" , "M1234RGT78B" , "email@gmail.com" ,"To, Street" ,
                ClientType.P , "0123456789" ,  "wrongpass12");
        PasswordValidation validation = new PasswordValidation();
        Assertions.assertThrows(ValidationException.class , () -> {
            validation.validate(clientDto);
        });
    }
}
