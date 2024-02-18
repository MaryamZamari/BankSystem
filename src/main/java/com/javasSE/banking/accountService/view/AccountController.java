package com.javasSE.banking.accountService.view;

import com.javasSE.banking.accountService.dto.AccountDto;
import com.javasSE.banking.accountService.exception.TransactionUnsuccessfulException;
import com.javasSE.banking.clientService.clientException.ClientNotFoundException;
import com.javasSE.banking.common.model.DocFile;
import com.javasSE.banking.common.model.FileType;
import com.javasSE.banking.common.utility.ScannerWrapperUtil;
import com.javasSE.banking.accountService.facade.AccountFacade;
import com.javasSE.banking.accountService.facade.IAccountFacade;
import com.javasSE.banking.accountService.exception.AccountNotFoundException;
import com.javasSE.banking.accountService.exception.DuplicateAccountException;
import com.javasSE.banking.accountService.exception.EmptyAccountException;
import com.javasSE.banking.common.exception.ValidationException;

import java.security.InvalidParameterException;

import static java.lang.Integer.parseInt;

public class AccountController {
    private static final AccountController INSTANCE;
    private final IAccountFacade accountFacade;
    private final ScannerWrapperUtil scannerWrapper;
    private final AccountConsole view;
    private AccountController(){
        view = AccountConsole.getInstance();
        scannerWrapper = ScannerWrapperUtil.getInstance();
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
        int choice = 0;
        try {
            do {
                view.printAccountMenu();
                System.out.println();
                choice = scannerWrapper.getUserInput("Enter your choice: " , Integer::valueOf);
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