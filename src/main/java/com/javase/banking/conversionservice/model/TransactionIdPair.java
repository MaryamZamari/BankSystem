package com.javase.banking.conversionservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class TransactionIdPair {
    private Integer sourceId;
    private Integer destinationId;
}
