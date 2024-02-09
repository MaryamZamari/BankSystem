package com.javase.banking.conversionservice.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@ToString
public class Transaction {
    private CurrencyPair currencyPair;
    private ConversionRate conversionRate;
    private Date timeStamp;
    private BigDecimal amount;




}
