package com.javase.banking;

import com.javase.banking.shared.view.BankController;

public class ApplicationRunner {
    private static final BankController controller= BankController.getInstance();

    public static void main(String[] args){
            controller.run();
        }

}
