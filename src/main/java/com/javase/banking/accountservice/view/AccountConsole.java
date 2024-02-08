package com.javase.banking.accountservice.view;

import com.javase.banking.accountservice.dto.AccountDto;
import com.javase.banking.accountservice.model.AccountType;
import com.javase.banking.clientservice.clientfacade.ClientFacade;
import com.javase.banking.shared.model.DocFile;
import com.javase.banking.shared.model.FileType;
import com.javase.banking.shared.utility.IdGeneratorUtil;
import com.javase.banking.shared.utility.ScannerWrapperUtil;
import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.text.ParseException;
import java.util.function.Function;

public class AccountConsole {
    private static final AccountConsole INSTANCE;
    private final ScannerWrapperUtil scannerWrapper;
    private final ClientFacade clientFacade= ClientFacade.getInstance();
    private AccountConsole(){
        scannerWrapper= ScannerWrapperUtil.getInstance();
    }
    static{
        INSTANCE= new AccountConsole();
    }
    public static AccountConsole getInstance() {
        return INSTANCE;
    }

    public void printAccountMenu() {
        System.out.println();
        System.out.println("Welcome to Account Management Portal! \n" +
                "--- select a menu item: --- \n" +
                "0.Exit\n" +
                "1.Add a new account.\n" +
                "2.Search an account \n" +
                "3.Edit an account \n" +
                "4.Remove an account  \n" +
                "5.Printing all the accounts.\n" +
                "6.Printing all the deleted accounts.\n" +
                "7.Save data.\n" +
                "8.Load data.\n" +
                "9.Add data.\n"
        );
    }


    public AccountDto getAccountDetailsFromUser() throws ParseException {
        AccountDto newAccount = null;
        char accountType = scannerWrapper.getUserInput("what type of client? " +
                                                                "E: Euro,  " +
                                                                "D: Dollar. ", x -> x.toUpperCase().charAt(0));
        scannerWrapper.clearExcessiveInput();
        AccountType type = switch(accountType){
            case 'E' -> AccountType.EURO;
            case 'D' -> AccountType.DOLLAR;
            default -> throw new IllegalStateException("Unexpected value: " + accountType);
        };
        String name= scannerWrapper.getUserInput("enter account name: ", Function.identity());
        String number = scannerWrapper.getUserInput("enter account number: ", Function.identity());
        BigDecimal balance= scannerWrapper.getUserInput("Enter balance: ", BigDecimal::new);
        int clientId= scannerWrapper.getUserInput("Enter client id: ", Integer::valueOf);
        newAccount= new AccountDto(IdGeneratorUtil.generateUniqueAccountId(), name, number, type, balance, clientId);
        return newAccount;
    }

       public int getIdFromUser (){
        int id= scannerWrapper.getUserInput("enter Id: " , Integer::valueOf);
        return id;
    }
    public Object getAccountDetailForSelection() throws InvalidParameterException {
        char choice=
            scannerWrapper.getUserInput("How do you want to search for the account? \n" +
                                        "N.Account Number \n"+
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
       public DocFile getFileTypeFromUser(){
        DocFile file;
        char type = scannerWrapper.getUserInput("what type of File? " +
                "S: Serialised,  " +
                "J: JSON. ", x -> x.toUpperCase().charAt(0));
        String fileName= scannerWrapper.getUserInput("Enter the name of the file: ", Function.identity());
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
        String fileName= scannerWrapper.getUserInput("Enter the name of the file: ", Function.identity());
        return fileName;
    }

    public void addData() {
        String fileName= scannerWrapper.getUserInput("Enter the name of the JSON file: ", Function.identity());
        clientFacade.addData(fileName);
    }

    public AccountDto getAccountDetailsFromUserForEdit(AccountDto oldAccount) {
        //TODO: implement getAccountDetailsFromUserForEdit
        return null;
    }
}


