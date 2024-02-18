package com.javaSe.banking.test.clientServiceTest.validationTest;

import com.javasSE.banking.clientService.clientValidation.EmailValidation;
import com.javasSE.banking.clientService.dto.ClientDto;
import com.javasSE.banking.clientService.dto.PersonalClientDto;
import com.javasSE.banking.clientService.model.ClientType;
import com.javasSE.banking.common.exception.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class EmailValidationTest {
    @Test
    public void emailWrongFormatValidationTest(){
        Date birthDate = new Date("04-Apr-1990");
        ClientDto clientDto = new PersonalClientDto(0 , "name" , "surname" , birthDate ,
                "iranian" , "M1234RGT78B" , "badEmail" ,"To, Street" ,
                          ClientType.P , "0123456789" ,  "Abdn2024!");
        EmailValidation validation = new EmailValidation();
        Assertions.assertThrows(ValidationException.class , () ->
        {
            validation.validate(clientDto);
        });
    }

    @Test
    public void nullEmailFormatValidationTest(){
        Date birthDate = new Date("04-Apr-1990");
        ClientDto clientDto = new PersonalClientDto(0 , "name" , "surname" , birthDate ,
                "iranian" , "M1234RGT78B" , null ,"To, Street" ,
                ClientType.P , "0123456789" ,  "Abdn2024!");
        EmailValidation validation = new EmailValidation();
        Assertions.assertThrows(ValidationException.class , () ->
        {
            validation.validate(clientDto);
        });
    }
}
