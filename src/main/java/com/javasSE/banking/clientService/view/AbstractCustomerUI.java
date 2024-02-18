package com.javasSE.banking.clientService.view;

import com.javasSE.banking.clientService.dto.ClientDto;
import com.javasSE.banking.clientService.model.ClientType;
import com.javasSE.banking.common.utility.ScannerWrapperUtil;

import java.text.ParseException;
import java.util.function.Function;

public abstract class AbstractCustomerUI {
    static ScannerWrapperUtil scannerWrapper;
    private final ClientConsole console;
    public AbstractCustomerUI(ScannerWrapperUtil scannerWrapper){
        this.scannerWrapper= ScannerWrapperUtil.getInstance();
        console= ClientConsole.getInstance();
    }
    public abstract void editClient(ClientDto oldClient);
    public static AbstractCustomerUI createCustomerUI(ClientType type){
       return switch(type){
            case B ->  new LegalClientUI(scannerWrapper);
            case P ->  new PersonalClientUI(scannerWrapper);
        };
    }

    public ClientDto generateCustomer(ClientType type) throws ParseException {
        String name = scannerWrapper.getUserInput("Enter Name: ", Function.identity());
        String fiscalCode = scannerWrapper.getUserInput("Enter FiscalCode: ", Function.identity());
        String email = scannerWrapper.getUserInput("Enter new Email: ", Function.identity());
        String address = scannerWrapper.getUserInput("Enter new Address: ", Function.identity());
        boolean deleted= false;
        String passwordInput= scannerWrapper.getUserInput("Enter password: ", Function.identity());
        String numberInput= scannerWrapper.getUserInput("Enter number: ", Function.identity());
        System.out.println("client data obtained from the DTO. moving on to the specific client.");
        return additionalGenerateClient(name, fiscalCode, email, address, deleted, passwordInput, type, numberInput);
    }


    protected abstract ClientDto additionalGenerateClient(String name, String fiscalCode, String email,
                                                          String address, boolean deleted, String passwordInput,
                                                          ClientType type, String number) throws ParseException;
}
