package com.javase.banking.shared.view;

import com.javase.banking.accountservice.exception.TransactionUnsuccessfulException;

import java.util.Scanner;

public class BankController{
    private static final BankController INSTANCE;
    private final BankConsole bankConsole;
    private BankController(){
        bankConsole= BankConsole.getInstance();
    }
    static{
        INSTANCE= new BankController();
    }
    public static BankController getInstance(){
        return INSTANCE;
    }


    public void run(){
        saveOnExit();
        initData();
        Runtime.getRuntime().addShutdownHook(new Thread(this::saveOnExit));

        try(Scanner input= new Scanner(System.in)){
          bankConsole.printBankMenu();
          int choice= Integer.parseInt(input.nextLine());
          do {
              switch (choice) {
                  case 1 -> bankConsole.clientController.run();
                  case 2 -> bankConsole.accountController.run();
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
        } catch (TransactionUnsuccessfulException e) {
            throw new RuntimeException(e);
        }
    }
    private void saveOnExit(){
        bankConsole.saveOnExit();
    }
    private void initData(){
        bankConsole.initData();
    }
    private void saveData() {
    }

    private void loadData() {

    }


}
