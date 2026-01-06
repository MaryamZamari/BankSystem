package com.javasSE.banking.accountService.view;

import com.javasSE.banking.accountService.dto.AccountDto;
import com.javasSE.banking.accountService.exception.AccountNotFoundException;
import com.javasSE.banking.accountService.exception.TransactionUnsuccessfulException;
import com.javasSE.banking.common.exception.ValidationException;
import com.javasSE.banking.common.utility.FileIOUtil;
import com.javasSE.banking.common.view.BaseConsole;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.text.ParseException;
import java.util.Currency;
import java.util.List;
import java.util.function.Function;

public class AccountConsole extends BaseConsole {
    private static final AccountConsole INSTANCE;
    private FileIOUtil fileIO;

    private AccountConsole() {
        super();
        fileIO = FileIOUtil.getInstance();
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
                "3.Edit an account \n" +                    //TODO: it performs the modification, but it anyway returns a validation error.
                "4.Remove an account  \n" +
                "5.Printing all the accounts.\n" +
                "6.Printing all the deleted accounts.\n" +
                "7.Search accounts by Client name.\n" +    //TODO: to test
                "8.Save data.\n" +                          //TODO: to test and fix - at the moment no file gets added in hard disk
                "9.Load data.\n" +                          //TODO: to implement and test
                "10.Add data.\n" +                          //TODO: to implement and test
                "11.Deposit.\n" +                           //TODO: to test
                "12.Withdraw.\n" +                          //TODO: to test
                "13.Transact.\n"                            //TODO: to test
        );
    }


    public AccountDto getAccountDetailsFromUser() throws ParseException {
        AccountDto newAccount = null;
        char currency = scannerWrapper.getUserInput("what type of account? " +
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
        Currency accountCurrency = switch (currency) {
            case 'E' -> Currency.getInstance("EUR");
            case 'D' -> Currency.getInstance("USD");
            default -> throw new IllegalStateException("Unexpected value: " + currency);
        };
        String name = scannerWrapper.getUserInput("enter account name: ", Function.identity());
        BigDecimal balance = scannerWrapper.getUserInput("Enter balance: ", BigDecimal::new);
        int clientId = scannerWrapper.getUserInput("Enter client id: ", Integer::valueOf); //TODO: manage existence of client in DB. if there is no client with that id already registered, you should provoke the client system first.
        newAccount = new AccountDto(name, accountCurrency, balance, clientId);
        System.out.println("accountDto received from user: " + newAccount.toString());
        return newAccount;
    }

    public int getIdFromUser() {
        return scannerWrapper.getUserInput("enter Id: ", Integer::valueOf);
    }

    public Integer getAccountDetailForSelection() throws InvalidParameterException {
        return scannerWrapper.getUserInput("enter id: ", Integer::valueOf);
        }



    public void addData() {
        String fileName = fileIO.getFileNameFromUser();
        clientFacade.addData(fileName);
    }

    public void initData() {
        accountFacade.initData();
    }

    public void saveOnExit() {
        accountFacade.saveOnExit();
    }

    public AccountDto getAccountDetailsFromUserForEdit(AccountDto oldAccountDto, int accountId) throws ValidationException, AccountNotFoundException {
          String name = scannerWrapper.getUserInput("enter account name: ", Function.identity());
          int clientId = scannerWrapper.getUserInput("Enter client id: ", Integer::valueOf);
        oldAccountDto.setName(name);
        oldAccountDto.setClientId(clientId);
        accountFacade.updateAccount(accountId , oldAccountDto);
        return oldAccountDto;
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


