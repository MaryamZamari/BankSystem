package com.javase.banking.service.exception.clientexception;

public class DuplicateClientException extends Throwable {
    String message;
    public DuplicateClientException(String messages) {
        this.message= message;
    }
}
