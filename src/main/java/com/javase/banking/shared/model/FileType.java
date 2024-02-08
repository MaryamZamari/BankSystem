package com.javase.banking.shared.model;

import com.javase.banking.clientservice.clientexception.InvalidClientType;

public enum FileType {
    SERIALISED('S'),
    JSON('J');
    private final char value;

    FileType(char value) {
        this.value = value;
    }

    public static FileType fromValue(char value) throws InvalidClientType {
        for(FileType type: values()){
            if(type.value == value){
                return type;
            }
        }
        throw new InvalidClientType();
    }
}