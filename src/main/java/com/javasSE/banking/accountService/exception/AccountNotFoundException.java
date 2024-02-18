package com.javasSE.banking.accountService.exception;

public class AccountNotFoundException extends Throwable{
    public AccountNotFoundException(){
        super("Account not found exception");
    }
}
