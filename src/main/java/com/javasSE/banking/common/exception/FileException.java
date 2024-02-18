package com.javasSE.banking.common.exception;

public class FileException extends Throwable {
    private String message;
    public FileException(){
        super("Error while working with the files!");
    }
}
