package com.javasSE.banking.clientService.model;

public enum ClientType {
    B("BUSINESS"),
    P("PERSONAL");

    private String label;

    ClientType(String label) {
        this.label= label;
    }
}
