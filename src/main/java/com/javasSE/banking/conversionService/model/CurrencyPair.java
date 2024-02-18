package com.javasSE.banking.conversionService.model;

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
