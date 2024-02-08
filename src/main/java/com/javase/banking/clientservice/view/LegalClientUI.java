package com.javase.banking.clientservice.view;

import com.javase.banking.clientservice.dto.ClientDto;
import com.javase.banking.clientservice.dto.LegalClientDto;
import com.javase.banking.clientservice.model.ClientType;
import com.javase.banking.shared.utility.ScannerWrapperUtil;
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
        ((LegalClientDto) oldClient).setWebsite(website);
        ((LegalClientDto) oldClient).setEmployeeCount(count);    //TODO: check this thing later. should u send a part of the logic in service?
    }

    @Override
    protected ClientDto additionalGenerateClient(Integer id, String name, String fiscalCode, String email, String address,
                                                 boolean deleted, String passwordInput, ClientType type, String numbers) {
        String person = null;
        String industry = null;
        String registrationNumber = null;
        String date = null;
        Date estDate = null;
        String website = null;
        int count= 0;
        try{
            person = scannerWrapper.getUserInput("enter Contact Person: ", Function.identity());
            industry = scannerWrapper.getUserInput("enter Industry: ", Function.identity());
            registrationNumber = scannerWrapper.getUserInput("enter Registration Number: ", Function.identity());
            date = scannerWrapper.getUserInput("enter Establishment Date (dd-MM-yyyy): ", Function.identity());
            estDate = new SimpleDateFormat("dd-MM-yyyy").parse(date);
            website = scannerWrapper.getUserInput("enter Website: " , Function.identity());
            count = scannerWrapper.getUserInput("enter Employee Count: ", Integer::valueOf);
        }catch(ParseException exception){
            exception.printStackTrace();
        }
        ClientDto legalClientDto= new LegalClientDto(null , type, name, person, industry, fiscalCode,
                                                    registrationNumber, estDate, email, website, address,
                                                    count, numbers, passwordInput);
        return legalClientDto;
    }


}
