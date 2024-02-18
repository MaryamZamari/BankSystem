package com.javasSE.banking.clientService.view;

import com.javasSE.banking.clientService.dto.ClientDto;
import com.javasSE.banking.clientService.dto.LegalClientDto;
import com.javasSE.banking.clientService.model.ClientType;
import com.javasSE.banking.common.utility.ScannerWrapperUtil;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Function;

public class LegalClientUI extends AbstractCustomerUI {
    public LegalClientUI(ScannerWrapperUtil scannerWrapper) {
        super(scannerWrapper);
    }

    @Override
    public void editClient(ClientDto oldClient) {
        String person= scannerWrapper.getUserInput("enter new Contact Person: " , Function.identity());
        String industry= scannerWrapper.getUserInput("enter new Industry: ", Function.identity());
        String website= scannerWrapper.getUserInput("enter new website: ", Function.identity());
        int count= scannerWrapper.getUserInput("enter new Employee Count: " , Integer::valueOf);

        ((LegalClientDto) oldClient).setContactPerson(person);
        ((LegalClientDto) oldClient).setIndustry(industry);
    }

    @Override
    protected ClientDto additionalGenerateClient(Integer id, String name, String fiscalCode, String email, String address,
                                                 boolean deleted, String passwordInput, ClientType type, String numbers) {
        String person = null;
        String industry = null;
        String registrationNumber = null;
        String date = null;
        Date estDate = null;
        int count= 0;
        try{
            person = scannerWrapper.getUserInput("enter Contact Person: ", Function.identity());
            industry = scannerWrapper.getUserInput("enter Industry: ", Function.identity());
            registrationNumber = scannerWrapper.getUserInput("enter Registration Number: ", Function.identity());
            date = scannerWrapper.getUserInput("enter Establishment Date (dd-MM-yyyy): ", Function.identity());
            estDate = new SimpleDateFormat("dd-MM-yyyy").parse(date);
        }catch(ParseException exception){
            exception.printStackTrace();
        }
        ClientDto legalClientDto= new LegalClientDto(null , type, name, person, industry, fiscalCode,
                                                    registrationNumber, estDate, email, address,
                                                    numbers, passwordInput);
        return legalClientDto;
    }


}
