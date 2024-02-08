package com.javase.banking.clientservice.clientexception;

public class DuplicateClientException extends Throwable {
    String message;
    public DuplicateClientException(String messages) {
        this.message= message;
    }
}
