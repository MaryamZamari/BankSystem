package com.javasSE.banking.common.view;

import com.javasSE.banking.accountService.exception.TransactionUnsuccessfulException;
import com.javasSE.banking.common.utility.ScannerWrapperUtil;

public class BankController{
    private static final BankController INSTANCE;
    private final ScannerWrapperUtil scannerWrapper;
    private final BankConsole bankConsole;
    private BankController(){
        scannerWrapper = ScannerWrapperUtil.getInstance();
        bankConsole = BankConsole.getInstance();
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
        int choice= 0;
        try{
          do {
              bankConsole.printBankMenu();
              choice= scannerWrapper.getUserInput("Enter your choice: " , Integer::valueOf);
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
        }catch (TransactionUnsuccessfulException e) {
            throw new RuntimeException(e);
        }catch(Exception e){
            e.printStackTrace();
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
