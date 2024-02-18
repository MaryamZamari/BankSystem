package com.javasSE.banking.conversionService.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ConversionRateNotFoundException extends Throwable {
    private String message;

}
