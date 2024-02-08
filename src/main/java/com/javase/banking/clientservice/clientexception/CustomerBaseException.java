package com.javase.banking.clientservice.clientexception;

public class CustomerBaseException extends Exception {
    public CustomerBaseException(String message){
        super("Customer not found");
    }
}
