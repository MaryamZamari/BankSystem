package com.javase.banking.model.client;

public enum ClientType {
    B("BUSINESS"),
    P("PERSONAL");

    private String label;

    ClientType(String label) {
        this.label= label;
    }
}
