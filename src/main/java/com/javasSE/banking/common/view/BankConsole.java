package com.javasSE.banking.common.view;

import com.javasSE.banking.clientService.view.ClientController;
import com.javasSE.banking.accountService.view.AccountController;

public class BankConsole {
    private static final BankConsole INSTANCE;
    protected final ClientController clientController;
    protected final AccountController accountController;
    private BankConsole(){
        clientController= ClientController.getInstance();
        accountController= AccountController.getInstance();
    }
    static{
        INSTANCE = new BankConsole();
    }
    public static BankConsole getInstance() {
        return INSTANCE;
    }

    public void printBankMenu(){
        System.out.println("Welcome to the Banking Portal! \n" +
                "--- select a menu item: --- \n" +
                "0.Exit. \n" +
                "1.Client system \n" +
                "2.Account System \n" +
                "3.Load Data \n" +
                "4.Save Data \n"
        );
    }

    public void initData(){
        clientController.initData();
        accountController.initData();
    }
    public void saveOnExit(){
        clientController.saveOnExit();
        accountController.saveOnExit();
    }
}
