package com.javase.banking.service.exception;

public class FileException extends Throwable {
    private String message;
    public FileException(){
        super("Error while working with the files!");
    }
}
