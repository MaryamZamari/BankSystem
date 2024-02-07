package com.javase.banking.service.exception.clientexception;

public class CustomerBaseException extends Exception {
    public CustomerBaseException(String message){
        super("Customer not found");
    }
}
