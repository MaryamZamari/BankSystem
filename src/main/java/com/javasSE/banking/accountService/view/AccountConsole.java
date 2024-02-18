package com.javasSE.banking.accountService.view;

import com.javasSE.banking.accountService.dto.AccountDto;
import com.javasSE.banking.accountService.exception.AccountNotFoundException;
import com.javasSE.banking.accountService.exception.TransactionUnsuccessfulException;
import com.javasSE.banking.accountService.model.AccountType;
import com.javasSE.banking.common.exception.ValidationException;
import com.javasSE.banking.common.model.DocFile;
import com.javasSE.banking.common.model.FileType;
import com.javasSE.banking.common.utility.IdGeneratorUtil;
import com.javasSE.banking.common.view.BaseConsole;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.text.ParseException;
import java.util.List;
import java.util.function.Function;

public class AccountConsole extends BaseConsole {
    private static final AccountConsole INSTANCE;

    private AccountConsole() {
        super();
    }

    static {
        INSTANCE = new AccountConsole();
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
                "7.Search accounts by Client name.\n" +
                "8.Save data.\n" +
                "9.Load data.\n" +
                "10.Add data.\n" +
                "11.Deposit.\n" +
                "12.Withdraw.\n" +
                "13.Transact.\n"
        );
    }


    public AccountDto getAccountDetailsFromUser() throws ParseException {
        AccountDto newAccount = null;
        char accountType = scannerWrapper.getUserInput("what type of client? " +
                        "E: Euro,  " +
                        "D: Dollar. ",
                x -> {
                    try {
                        return x.toUpperCase().charAt(0);
                    } catch (IllegalStateException exception) {
                        System.out.println("You entered a wrong character by mistake, Enter a character from the menu");
                        throw new RuntimeException();
                    }
                });
        AccountType type = switch (accountType) {
            case 'E' -> AccountType.EURO;
            case 'D' -> AccountType.DOLLAR;
            default -> throw new IllegalStateException("Unexpected value: " + accountType);
        };
        String name = scannerWrapper.getUserInput("enter account name: ", Function.identity());
        String number = scannerWrapper.getUserInput("enter account number: ", Function.identity());
        BigDecimal balance = scannerWrapper.getUserInput("Enter balance: ", BigDecimal::new);
        int clientId = scannerWrapper.getUserInput("Enter client id: ", Integer::valueOf);
        newAccount = new AccountDto(IdGeneratorUtil.generateUniqueAccountId(), name, number, type, balance, clientId);
        return newAccount;
    }

    public int getIdFromUser() {
        int id = scannerWrapper.getUserInput("enter Id: ", Integer::valueOf);
        return id;
    }

    public Object getAccountDetailForSelection() throws InvalidParameterException {
        char choice =
                scannerWrapper.getUserInput("How do you want to search for the account? \n" +
                        "N.Account Number \n" +
                        "I.Id\n", x -> {
                    try {
                        return x.toUpperCase().charAt(0);
                    } catch (IllegalStateException exception) {
                        System.out.println("You entered a wrong character by mistake. Enter a character from the menu");
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

    //=========== File and saving related methods ===========
    public DocFile getFileTypeFromUser() {
        DocFile file;
        char type = scannerWrapper.getUserInput("what type of File? " +
                "S: Serialised,  " +
                "J: JSON. ",
                x -> {
                    try{
                        return x.toUpperCase().charAt(0);
                    }catch(IllegalStateException exception){
                        System.out.println("You entered a wrong character by mistake, Enter a character from the menu");
                        throw new RuntimeException();
                    }
                });
        String fileName = scannerWrapper.getUserInput("Enter the name of the file: ", Function.identity());
        FileType fileType = switch (type) {
            case 'S' -> FileType.SERIALISED;
            case 'J' -> FileType.JSON;
            default -> throw new IllegalStateException("Unexpected value: " + type);
        };
        file = new DocFile(fileName, fileType);
        return file;
    }

    public String getFileNameFromUser() {
        String fileName = scannerWrapper.getUserInput("Enter the name of the file: ", Function.identity());
        return fileName;
    }

    public void addData() {
        String fileName = scannerWrapper.getUserInput("Enter the name of the JSON file: ", Function.identity());
        clientFacade.addData(fileName);
    }

    public void initData() {
        accountFacade.initData();
    }

    public void saveOnExit() {
        accountFacade.saveOnExit();
    }

    //===========End of File and saving related methods ===========

    public AccountDto getAccountDetailsFromUserForEdit(AccountDto oldAccount) {
        //TODO: implement getAccountDetailsFromUserForEdit
        return null;
    }

    public void searchAccountByClientName() {
        String name = scannerWrapper.getUserInput("Enter client name", Function.identity());
        List<AccountDto> clients = accountFacade.searchAccountByClientName(name);
        clients.forEach(System.out::println);
    }

    public void deposit() throws AccountNotFoundException {
        int accountId = scannerWrapper.getUserInput("Enter account Id: ", Integer::valueOf);
        BigDecimal amount = scannerWrapper.getUserInput("Enter the amount to deposit: ", BigDecimal::new);
        accountFacade.deposit(accountId, amount);
    }

    public void withdraw() throws ValidationException, AccountNotFoundException {
        int accountId = scannerWrapper.getUserInput("Enter account Id: ", Integer::valueOf);
        BigDecimal amount = scannerWrapper.getUserInput("Enter the amount to deposit: ", BigDecimal::new);
        accountFacade.withdraw(accountId, amount);
    }

    public void transfer() throws ValidationException, AccountNotFoundException, TransactionUnsuccessfulException {
        try {
            int sourceAccountId = scannerWrapper.getUserInput("Enter the source account id: ", Integer::valueOf);
            int destinationAccountId = scannerWrapper.getUserInput("Enter the destination account id: ", Integer::valueOf);
            BigDecimal amount = scannerWrapper.getUserInput("Enter the amount to deposit: ", BigDecimal::new);
            accountFacade.transfer(sourceAccountId, destinationAccountId, amount);
        } catch (TransactionUnsuccessfulException | ValidationException | AccountNotFoundException exception) {
            throw new TransactionUnsuccessfulException("Transaction unsuccessful!");
        }
    }


}


