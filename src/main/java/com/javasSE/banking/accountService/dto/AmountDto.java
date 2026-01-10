package com.javasSE.banking.accountService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Currency;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AmountDto {
    private Currency currency;
    private BigDecimal value;
}
