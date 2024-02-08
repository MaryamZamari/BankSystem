package com.javase.banking.accountservice.view;

import com.javase.banking.accountservice.accountfacade.AccountFacade;
import com.javase.banking.accountservice.accountfacade.IAccountFacade;
import com.javase.banking.accountservice.dto.AccountDto;
import com.javase.banking.accountservice.exception.AccountNotFoundException;
import com.javase.banking.shared.model.DocFile;
import com.javase.banking.shared.model.FileType;
import java.security.InvalidParameterException;
import java.util.Scanner;
import static java.lang.Integer.parseInt;

public class AccountController {
    private static final AccountController INSTANCE;
    private final IAccountFacade accountFacade;
    private final AccountConsole view;
    private AccountController(){
        view = AccountConsole.getInstance();
        accountFacade= AccountFacade.getInstance();
    }
    static{
        INSTANCE= new AccountController();
    }
    public static AccountController getInstance() {
        return INSTANCE;
    }

    public void run() {
        accountFacade.initData();
        Runtime.getRuntime().addShutdownHook(new Thread(this::saveOnExit));
        try(Scanner input= new Scanner(System.in)) {
            int choice = 0;
            do {
                view.printAccountMenu();
                System.out.println();
                choice = parseInt(input.nextLine());
                switch (choice) {
                    case 1:
                        AccountDto newAccount = view.getAccountDetailsFromUser();
                        addAccount(newAccount);
                        break;
                    case 2:
                        Object accountDetailToSearch = null;
                        try {
                            accountDetailToSearch = view.getAccountDetailForSelection();
                        } catch (InvalidParameterException exception) {
                            System.out.println("you typed the wrong characters. revise your choice to select the client!");
                        }
                        searchClient(accountDetailToSearch);
                        break;
                    case 3:
                        int accountId = view.getIdFromUser();
                        AccountDto oldAccount = accountFacade.getAccountById(accountId);
                        AccountDto updatedAccount = view.getAccountDetailsFromUserForEdit(oldAccount);
                        updateAccount(accountId, updatedAccount);
                        break;
                    case 4:
                        accountId = view.getIdFromUser();
                        deleteAccount(accountId);
                        break;
                    case 5:
                        printAllAccounts();
                        break;
                    case 6:
                        printAllDeletedAccounts();
                        break;
                    case 7:
                        DocFile file = view.getFileTypeFromUser();
                        saveData(file);
                        break;
                    case 8:
                        file = view.getFileTypeFromUser();
                        loadData(file.getType());
                        break;
                    case 9:
                        view.addData();
                    default:
                        if (choice != 0) {
                            System.out.println("the selected number is invalid. try again!");
                        }
                }
            } while (choice != 0);
        }catch(Exception e){
            System.out.println("error in Account Controller");
             } catch (AccountNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void printAllDeletedAccounts() {
    }

    //TODO: implement these methods
    private void printAllAccounts() {
    }

    private void deleteAccount(int accountId) {
    }

    private void saveData(DocFile file) {
    }

    private void loadData(FileType type) {
    }

    private void updateAccount(int accountId, AccountDto updatedAccount) {

    }

    private void searchClient(Object clientDetailToSearch) {
    }

    private void saveOnExit() {
    }

    private void addAccount(AccountDto newAccount) {
    }
}