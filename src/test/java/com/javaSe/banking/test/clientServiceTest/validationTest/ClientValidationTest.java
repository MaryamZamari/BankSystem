package com.javaSe.banking.test.clientServiceTest.validationTest;

import com.javasSE.banking.clientService.clientValidation.ClientValidation;
import com.javasSE.banking.clientService.dto.ClientDto;
import com.javasSE.banking.clientService.dto.PersonalClientDto;
import com.javasSE.banking.clientService.model.ClientType;
import com.javasSE.banking.common.exception.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.Date;

public class ClientValidationTest {
    @Test
    public void nullNameValidationTest(){
        Date birthDate = new Date("04-Apr-1990");
        ClientDto clientDto =
                new PersonalClientDto(0 , null , "surname" , birthDate , "iranian" ,  "M1234RGT78B" , "example@gmail.com"
                                    ,"To, Street" , ClientType.P , "0123456789" ,  "Abdn2024!");
        ClientValidation validation = new ClientValidation();
        Assertions.assertThrows(ValidationException.class , () -> {
            validation.validate(clientDto);
        });
    }

    @Test
    public void emptyNameValidationTest(){
        Date birthDate = new Date("04-Apr-1990");
        ClientDto clientDto =
                new PersonalClientDto(0 , " " , "surname" , birthDate , "iranian" ,
                        "M1234RGT78B" , "example@gmail.com" ,"To, Street" , ClientType.P ,
                          "0123456789" ,  "Abdn2024!");
        ClientValidation validation = new ClientValidation();
        Assertions.assertThrows(ValidationException.class , () -> {
            validation.validate(clientDto);
        });
    }

}
