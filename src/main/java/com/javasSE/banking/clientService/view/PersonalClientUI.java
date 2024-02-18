package com.javasSE.banking.clientService.view;


import com.javasSE.banking.clientService.dto.ClientDto;
import com.javasSE.banking.clientService.dto.PersonalClientDto;
import com.javasSE.banking.clientService.model.ClientType;
import com.javasSE.banking.common.utility.ScannerWrapperUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Function;

public class PersonalClientUI extends AbstractCustomerUI {
    public PersonalClientUI(ScannerWrapperUtil scannerWrapper) {
        super(scannerWrapper);
    }

    @Override
    public void editClient(ClientDto oldClient) {
        String surname = scannerWrapper.getUserInput("Enter new surname: ", Function.identity());
        String nationality = scannerWrapper.getUserInput("Enter new nationality: ", Function.identity());

        ((PersonalClientDto) oldClient).setSurname(surname);
        ((PersonalClientDto) oldClient).setNationality(nationality);
    }

    @Override
    protected ClientDto additionalGenerateClient(String name, String fiscalCode, String email,
                                                 String address, boolean deleted, String passwordInput,
                                                 ClientType type, String number) throws ParseException {
        String surname = null;
        Date birthdate = null;
        String nationality = null;
        surname = scannerWrapper.getUserInput("Enter surname: ", Function.identity());
        birthdate = scannerWrapper.getUserInput("Enter birthdate (dd-MM-yyyy): ",
                x -> {
                    try {
                        return new SimpleDateFormat("dd-MM-yyyy").parse(x);
                    } catch (ParseException exception) {
                        System.out.println("Enter a correct date format.");
                        throw new RuntimeException();
                    }
                });
        nationality = scannerWrapper.getUserInput("Enter nationality: ", Function.identity());

        ClientDto personalClient =
                new PersonalClientDto(null, name, surname, birthdate, nationality,
                        fiscalCode, email, address, type, number, passwordInput);
        return personalClient;
    }


}
