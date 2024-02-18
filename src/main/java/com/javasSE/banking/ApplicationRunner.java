package com.javasSE.banking;

import com.javasSE.banking.common.view.BankController;

public class ApplicationRunner {
    private static final BankController controller = BankController.getInstance();

    public static void main(String[] args) {
        try {
            controller.run();
        } catch (Throwable exception) {
            System.out.println("System error!");
        }
    }

}
