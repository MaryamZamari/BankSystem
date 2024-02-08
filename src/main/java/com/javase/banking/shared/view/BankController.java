package com.javase.banking.shared.view;

import com.javase.banking.accountservice.view.AccountController;
import com.javase.banking.clientservice.view.ClientController;
import com.javase.banking.shared.utility.ScannerWrapperUtil;
import java.util.Scanner;

public class BankController {
    private static final BankController INSTANCE;
    private final ClientController clientController;
    private final AccountController accountController;
    private final ScannerWrapperUtil scanner;
    private BankController(){
        scanner= ScannerWrapperUtil.getInstance();
        clientController= ClientController.getInstance();
        accountController= AccountController.getInstance();
    }
    static{
        INSTANCE= new BankController();
    }
    public static BankController getInstance(){
        return INSTANCE;
    }

    public void printBankMenu(){
        System.out.println("Welcome to Client Management Portal! \n" +
                            "--- select a menu item: --- \n" +
                            "0.Exit\n" +
                            "1.Client system \n" +
                            "2.Bank System \n" +
                            "3.Load Data \n" +
                            "4.Save Data \n"
        );
    }
    public void run(){
        try(Scanner input= new Scanner(System.in)){
          printBankMenu();
          int choice= Integer.parseInt(input.nextLine());
          do {
              switch (choice) {
                  case 1 -> clientController.run();
                  case 2 -> accountController.run();
                  case 3 -> loadData();
                  case 4 -> saveData();
                  default -> {
                      if(choice != 0){
                          System.out.println("wrong choice!Try again");
                          run();
                      }
                  }
              }
          }while(choice != 0);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void saveData() {
    }

    private void loadData() {

    }

}
