package com.javase.banking.accountservice.exception;

public class AccountNotFoundException extends Throwable{
    public AccountNotFoundException(){
        super("Account not found exception");
    }
}
