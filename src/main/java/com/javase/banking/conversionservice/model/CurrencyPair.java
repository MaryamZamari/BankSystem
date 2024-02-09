package com.javase.banking.conversionservice.model;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyPair {
    private CurrencyType source;
    private CurrencyType destination;

}
