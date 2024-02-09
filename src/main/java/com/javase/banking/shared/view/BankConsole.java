package com.javase.banking.shared.view;

import com.javase.banking.accountservice.view.AccountController;
import com.javase.banking.clientservice.view.ClientController;
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
                "0.Exit\n" +
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
