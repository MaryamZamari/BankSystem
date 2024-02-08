package com.javase.banking.clientservice.view;

import com.javase.banking.clientservice.dto.ClientDto;
import com.javase.banking.clientservice.model.ClientType;
import com.javase.banking.shared.utility.ScannerWrapperUtil;
import java.util.function.Function;

public abstract class AbstractCustomerUI {
    static ScannerWrapperUtil scannerWrapper;
    private ClientConsole console = new ClientConsole();
    public AbstractCustomerUI(ScannerWrapperUtil scannerWrapper){
        this.scannerWrapper= ScannerWrapperUtil.getInstance();
    }
    public abstract void editClient(ClientDto oldClient);
    public static AbstractCustomerUI createCustomerUI(ClientType type){
       return switch(type){
            case B ->  new LegalClientUI(scannerWrapper);
            case P ->  new PersonalClientUI(scannerWrapper);
        };
    }

    public ClientDto generateCustomer(ClientType type){
        Integer id= null;
        String name = scannerWrapper.getUserInput("Enter Name: ", Function.identity());
        String fiscalCode = scannerWrapper.getUserInput("Enter FiscalCode: ", Function.identity());
        String email = scannerWrapper.getUserInput("Enter new Email: ", Function.identity());
        String address = scannerWrapper.getUserInput("Enter new Address: ", Function.identity());
        boolean deleted= false;
        String passwordInput= scannerWrapper.getUserInput("Enter password: ", Function.identity());
        String numberInput= scannerWrapper.getUserInput("Enter number: ", Function.identity());
        System.out.println("client data obtained. moving on to the specific client. id is : " + id );
        return additionalGenerateClient(id, name, fiscalCode, email, address, deleted, passwordInput, type, numberInput);
    }


    protected abstract ClientDto additionalGenerateClient(Integer id, String name, String fiscalCode, String email,
                                                          String address, boolean deleted, String passwordInput,
                                                          ClientType type, String number);
}
