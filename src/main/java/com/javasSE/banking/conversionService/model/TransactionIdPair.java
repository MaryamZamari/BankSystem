package com.javasSE.banking.conversionService.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TransactionIdPair {
    private Integer sourceId;
    private Integer destinationId;
}
