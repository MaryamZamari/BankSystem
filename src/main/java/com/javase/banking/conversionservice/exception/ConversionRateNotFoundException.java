package com.javase.banking.conversionservice.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ConversionRateNotFoundException extends Throwable {
    private String message;

}
