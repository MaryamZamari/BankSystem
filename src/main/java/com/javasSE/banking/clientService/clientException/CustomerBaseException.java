package com.javasSE.banking.clientService.clientException;

public class CustomerBaseException extends Exception {
    public CustomerBaseException(String message){
        super("Customer not found");
    }
}
