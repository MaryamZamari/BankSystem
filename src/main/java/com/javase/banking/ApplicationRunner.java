package com.javase.banking;

import com.javase.banking.clientservice.view.ClientController;

public class ApplicationRunner {
    private static final ClientController controller= new ClientController();
    public static void main(String[] args){
            controller.run();
        }

}
