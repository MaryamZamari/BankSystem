package com.javasSE.banking.clientService.clientException;

public class DuplicateClientException extends Throwable {
    String message;
    public DuplicateClientException(String messages) {
        this.message= message;
    }
}
