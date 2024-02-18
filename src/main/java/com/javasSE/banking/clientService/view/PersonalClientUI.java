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
        String surname= scannerWrapper.getUserInput("Enter new surname: " , Function.identity());
        String nationality= scannerWrapper.getUserInput("Enter new nationality: " , Function.identity());

        ((PersonalClientDto) oldClient).setSurname(surname);
        ((PersonalClientDto) oldClient).setNationality(nationality);
    }

    @Override
    protected ClientDto additionalGenerateClient(Integer id, String name, String fiscalCode, String email,
                                                 String address, boolean deleted, String passwordInput,
                                                 ClientType type, String number) {
        String surname = null;
        String date = null;
        Date birthdate = null;
        String nationality = null;
        try{
            surname = scannerWrapper.getUserInput("Enter surname: ", Function.identity());
            date = scannerWrapper.getUserInput("Enter birthdate (dd-MM-yyyy): ", Function.identity());
            birthdate = new SimpleDateFormat("dd-MM-yyyy").parse(date);
            nationality = scannerWrapper.getUserInput("Enter nationality: ", Function.identity());
        }catch(ParseException exception){
            exception.printStackTrace(); //TODO: fix the date format and delete stack trace
        }
        ClientDto personalClient=
                new PersonalClientDto(null ,name , surname, birthdate, nationality,
                                        fiscalCode, email ,address ,type , number, passwordInput);
        return personalClient;
    }


}
