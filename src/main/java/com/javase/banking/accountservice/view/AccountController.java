package com.javase.banking.accountservice.view;

import com.javase.banking.accountservice.exception.TransactionUnsuccessfulException;
import com.javase.banking.accountservice.facade.AccountFacade;
import com.javase.banking.accountservice.facade.IAccountFacade;
import com.javase.banking.accountservice.dto.AccountDto;
import com.javase.banking.accountservice.exception.AccountNotFoundException;
import com.javase.banking.accountservice.exception.DuplicateAccountException;
import com.javase.banking.accountservice.exception.EmptyAccountException;
import com.javase.banking.clientservice.clientexception.ClientNotFoundException;
import com.javase.banking.shared.exception.ValidationException;
import com.javase.banking.shared.model.DocFile;
import com.javase.banking.shared.model.FileType;

import javax.xml.transform.TransformerConfigurationException;
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

    public void run() throws TransactionUnsuccessfulException {
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
                        searchAccount(accountDetailToSearch);
                        break;
                    case 3:
                        int accountId = view.getIdFromUser();
                        AccountDto oldAccount = accountFacade.getAccountById(accountId);
                        AccountDto updatedAccount = view.getAccountDetailsFromUserForEdit(oldAccount);
                        updateAccount(updatedAccount);
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
                        view.searchAccountByClientName();
                    case 8:
                        DocFile file = view.getFileTypeFromUser();
                        saveAccountData(file);
                        break;
                    case 9:
                        file = view.getFileTypeFromUser();
                        loadAccountData(file.getType());
                        break;
                    case 10:
                        view.addData();
                    case 11:
                        deposit();
                        break;
                    case 12:
                        withdraw();
                    case 13:
                        transfer();
                    default:
                        if (choice != 0) {
                            System.out.println("the selected number is invalid. try again!");
                        }
                }
            } while (choice != 0);
        } catch (AccountNotFoundException e) {
            throw new RuntimeException(e);
        } catch (TransactionUnsuccessfulException e) {
            throw new TransactionUnsuccessfulException("Transaction was not successful!");
        } catch (ClientNotFoundException e) {
            throw new RuntimeException("There is no client with this Id");
        }catch(Exception e){
            System.out.println("error in Account Controller");
        }
    }

    private void withdraw() throws ValidationException, AccountNotFoundException {
        view.withdraw();
    }

    private void transfer() throws ValidationException, AccountNotFoundException, TransactionUnsuccessfulException {
        view.transfer();
    }

    private void deposit() throws AccountNotFoundException {
        view.deposit();
    }
    private void searchAccount(Object accountDetailToSearch) throws AccountNotFoundException {
        accountFacade.getAccountByDetails(accountDetailToSearch); //TODO: check how you did it for client?
    }

    private void printAllDeletedAccounts() throws EmptyAccountException {
        accountFacade.getAllDeletedAccounts();
    }

    private void printAllAccounts() throws EmptyAccountException {
        accountFacade.getAllActiveAccounts();
    }

    private void deleteAccount(int accountId) throws AccountNotFoundException {
        accountFacade.deleteAccount(accountId);
    }
    private void saveAccountData(DocFile file) {
        accountFacade.saveAccountData(file);
    }

    private void loadAccountData(FileType type) {
        accountFacade.loadAccountData(type);
    }
    public void saveOnExit() {
    }
    public void initData() {
    }
    private void updateAccount(AccountDto updatedAccount) throws ValidationException, AccountNotFoundException {
        accountFacade.updateAccount(updatedAccount);
    }
    private void addAccount(AccountDto newAccount) throws DuplicateAccountException, ClientNotFoundException {
        accountFacade.addAccount(newAccount);
    }


}