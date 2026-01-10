package com.javasSE.banking.accountService.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.Currency;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Amount {
    private Currency currency;
    private BigDecimal value;
}
