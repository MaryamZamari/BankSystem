package com.javase.banking.service.exception.accountexception;

public class AccountNotFoundException extends Throwable{
    public AccountNotFoundException(){
        super("Account not found exception");
    }
}
