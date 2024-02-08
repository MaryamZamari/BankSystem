package com.javase.banking.clientservice.view;

import com.javase.banking.clientservice.dto.ClientDto;
import com.javase.banking.clientservice.clientfacade.ClientFacade;
import com.javase.banking.clientservice.model.ClientType;
import com.javase.banking.shared.model.DocFile;
import com.javase.banking.shared.model.FileType;
import com.javase.banking.shared.exception.ValidationException;
import com.javase.banking.shared.utility.ScannerWrapperUtil;

import java.security.InvalidParameterException;
import java.text.ParseException;
import java.util.function.Function;

public class ClientConsole {
    //menu + get details from user for various operations needed in the service classes.
    private final ScannerWrapperUtil scannerWrapper;
    private final ClientFacade clientFacade= ClientFacade.getInstance();
    public ClientConsole(){
        scannerWrapper= ScannerWrapperUtil.getInstance();
    }

    public void printClientMenu() {
        System.out.println();
        System.out.println("Welcome to Client Management Portal! \n" +
                "--- select a menu item: --- \n" +
                "0.Exit\n" +
                "1.Add a new client.\n" +
                "2.Search a client \n" +
                "3.Edit a client\n" +
                "4.Removing a client \n" +
                "5.Printing all the Clients.\n" +
                "6.Print all accounts of the client" +
                "7.Save data.\n" +
                "8.Load data.\n"  //TODO : CHECK the load data and see how to make it in a way that returning the clients would check the file first.
        );
    }


    public ClientDto getClientDetailsFromUser() throws ParseException {
        ClientDto newClient = null;
        AbstractCustomerUI customerUI = null;
        char clientType= scannerWrapper
                .getUserInput("what type of client? " +
                                "P: Personal,  " +
                                "B: Business. ", x -> x.toUpperCase().charAt(0));
        scannerWrapper.clearExcessiveInput();
        ClientType type = switch(clientType){
            case 'P' -> ClientType.P;
            case 'B' -> ClientType.B;
            default -> throw new IllegalStateException("Unexpected value: " + clientType);
        };
        newClient= AbstractCustomerUI.createCustomerUI(type).generateCustomer(type);
        return newClient;
    }

    public ClientDto getClientInfoFromUserForEdit(ClientDto oldClient) throws ValidationException {
        String name= scannerWrapper.getUserInput("Enter new Name: " , Function.identity());
        String email= scannerWrapper.getUserInput("Enter new Email: ", Function.identity());
        String address= scannerWrapper.getUserInput("Enter new Address: ", Function.identity());

        oldClient.setName(name);
        oldClient.setEmail(email);
        oldClient.setAddress(address);
        AbstractCustomerUI
                    .createCustomerUI(oldClient.getType())
                    .editClient(oldClient);
        clientFacade.updateClient(oldClient.getId(), oldClient);
        return oldClient;
    }

    public int getIdFromUser (){
        int id= scannerWrapper.getUserInput("enter Id: " , Integer::valueOf);
        return id;
    }
    public Object getClientDetailForSelection() throws InvalidParameterException {
        char choice=
            scannerWrapper.getUserInput("How do you want to search for the client? \n" +
                                        "N.Name\n"+
                                        "I.Id\n", x -> x.charAt(0));
        scannerWrapper.clearExcessiveInput();
        if(choice == 'N'){
            String name= scannerWrapper.getUserInput("enter name:", Function.identity());
            return name;
        }else if(choice == 'I'){
            int id= scannerWrapper.getUserInput("enter id: ", Integer::valueOf);
            return id;
        }else{
            throw new InvalidParameterException();
        }

    }
    public String getNewNumberToUpdate(){
       return scannerWrapper.getUserInput("enter new number: \n", Function.identity());
    }

    public DocFile getFileTypeFromUser(){
        DocFile file;
        char type = scannerWrapper.getUserInput("what type of File? " +
                "S: Serialised,  " +
                "J: JSON. ", x -> x.toUpperCase().charAt(0));
        String fileName= scannerWrapper
                            .getUserInput("Enter the name of the file: ", Function.identity());
        scannerWrapper.clearExcessiveInput();
        FileType fileType = switch(type){
            case 'S' -> FileType.SERIALISED;
            case 'J' -> FileType.JSON;
            default -> throw new IllegalStateException("Unexpected value: " + type);
        };
        file= new DocFile(fileName, fileType);
        return file;
    }


    public String getFileNameFromUser() {
        String fileName= scannerWrapper
                        .getUserInput("Enter the name of the file: ", Function.identity());
        return fileName;
    }

    public void addData() {
        String fileName= scannerWrapper
                        .getUserInput("Enter the name of the JSON file: ", Function.identity());
        clientFacade.addData(fileName);
    }
}


