package com.javasSE.banking.clientService.view;

import com.javasSE.banking.common.model.FileType;
import com.javasSE.banking.clientService.dto.ClientDto;
import com.javasSE.banking.clientService.model.ClientType;
import com.javasSE.banking.common.model.DocFile;
import com.javasSE.banking.common.exception.ValidationException;
import com.javasSE.banking.common.view.BaseConsole;

import java.security.InvalidParameterException;
import java.text.ParseException;
import java.util.function.Function;

public class ClientConsole extends BaseConsole {
    //menu + get details from user for various operations needed in the service classes.
    private static final ClientConsole INSTANCE;

    private ClientConsole() {
        super();
    }

    static {
        INSTANCE = new ClientConsole();
    }

    public static ClientConsole getInstance() {
        return INSTANCE;
    }

    public void printClientMenu() {
        System.out.println();
        System.out.println("Welcome to Client Management Portal! \n" +
                "--- select a menu item: --- \n" +  //TODO: check in controller and fix it.
                "0.Back to Bank menu \n" +
                "1.Add a new client.\n" +
                "2.Search a client \n" +
                "3.Edit a client\n" +     //TODO: at the moment, editing the client forces you to add all the data from the beginig. it is better to choose what they want to modify and then change only that.
                "4.Removing a client \n" +                      //TODO: check and test again
                "5.Printing all the Clients.\n" +
                "6.Print all the deleted clients.\n" +       //TODO: check and test again
                "7.Print all accounts of the client.\n" +   //TODO: i'm not sure if it is done or not.
                "8.Save data.\n" +               //TODO: check and test again
                "9.Load data.\n" +              //TODO: check and test again
                "10.Add data.\n"                //TODO: i'm not sure if it is done or not.
        );
    }


    public ClientDto getClientDetailsFromUser() throws ParseException {
        ClientDto newClient = null;
        AbstractCustomerUI customerUI = null;
        boolean validInput = false;
        char clientType = ' ';
        do {
            clientType = scannerWrapper
                    .getUserInput("what type of client? " +
                                    "P: Personal,  " +
                                    "B: Business. ",
                            x -> {
                                try {
                                    return x.toUpperCase().charAt(0);
                                } catch (IllegalStateException exception) {
                                    System.out.println("You entered a wrong character by mistake, " +
                                            "Enter a character from the menu");
                                    throw new RuntimeException();
                                }
                            });

            switch (clientType) {
            case 'P' -> validInput = true;
            case 'B' -> validInput = true;
            default ->  System.out.println("Unexpected value: " + clientType);
        };
        }while(!validInput);
        ClientType type = null;
        switch (clientType) {
            case 'P' -> type = ClientType.P;
            case 'B' -> type = ClientType.B;
            default ->  System.out.println("Unexpected value: " + clientType);
        };
        newClient = AbstractCustomerUI.createCustomerUI(type).generateCustomer(type);
        return newClient;
    }

    public ClientDto getClientInfoFromUserForEdit(ClientDto oldClient) throws ValidationException {
        String name = scannerWrapper.getUserInput("Enter new Name: ", Function.identity());
        String email = scannerWrapper.getUserInput("Enter new Email: ", Function.identity());
        String address = scannerWrapper.getUserInput("Enter new Address: ", Function.identity());

        oldClient.setName(name);
        oldClient.setEmail(email);
        oldClient.setAddress(address);
        AbstractCustomerUI
                .createCustomerUI(oldClient.getType())
                .editClient(oldClient);
        clientFacade.updateClient(oldClient.getId(), oldClient);
        return oldClient;
    }

    public int getIdFromUser() {
        int id = scannerWrapper.getUserInput("enter Id: ", Integer::valueOf);
        return id;
    }

    public Object getClientDetailForSelection() throws InvalidParameterException {
        char choice =
                scannerWrapper.getUserInput("How do you want to search for the client? \n" +
                                "N.Name\n" +
                                "I.Id\n",
                        x -> {
                            try {
                                return x.toUpperCase().charAt(0);
                            } catch (IllegalStateException exception) {
                                System.out.println("You entered a wrong character by mistake, Enter a character from the menu");
                                throw new RuntimeException();
                            }
                        });
        if (choice == 'N') {
            String name = scannerWrapper.getUserInput("enter name:", Function.identity());
            return name;
        } else if (choice == 'I') {
            int id = scannerWrapper.getUserInput("enter id: ", Integer::valueOf);
            return id;
        } else {
            throw new InvalidParameterException();
        }

    }

    public String getNewNumberToUpdate() {
        return scannerWrapper.getUserInput("enter new number: \n", Function.identity());
    }

    public DocFile getFileTypeFromUser() {
        DocFile file;
        char type = scannerWrapper.getUserInput("what type of File? " +
                        "S: Serialised,  " +
                        "J: JSON. ",
                x -> {
                    try {
                        return x.toUpperCase().charAt(0);
                    } catch (IllegalStateException exception) {
                        System.out.println("You entered a wrong character by mistake, Enter a character from the menu");
                        throw new RuntimeException();
                    }
                });
        String fileName = scannerWrapper.getUserInput("Enter the name of the file: ", Function.identity());
        FileType fileType = switch (type) {
            case 'S' -> FileType.SERIALISED;
            case 'J' -> FileType.JSON;
            default -> throw new IllegalStateException("Unexpected value: " + type); //TODO: remove this
        };
        file = new DocFile(fileName, fileType);
        return file;
    }


    public String getFileNameFromUser() {
        String fileName = scannerWrapper
                .getUserInput("Enter the name of the file: ", Function.identity());
        return fileName;
    }

    public void addData() {
        String fileName = scannerWrapper
                .getUserInput("Enter the name of the JSON file: ", Function.identity());
        clientFacade.addData(fileName);
    }

    public void saveOnExit() {
        clientFacade.saveOnExit();
    }
}


