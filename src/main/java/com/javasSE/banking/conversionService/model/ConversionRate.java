package com.javasSE.banking.conversionService.model;

import lombok.Getter;
import java.math.BigDecimal;
@Getter
public enum ConversionRate {
    USD_TO_EUR(new BigDecimal("0.85")),
    EUR_TO_USD(new BigDecimal("1.18")),
    GBP_TO_USD(new BigDecimal("1.38")),
    USD_TO_GBP(new BigDecimal("0.79"));
    private final BigDecimal rate;
    ConversionRate(BigDecimal rate) {
        this.rate= rate;
    }

}
