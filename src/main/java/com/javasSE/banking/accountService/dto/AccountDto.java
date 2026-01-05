package com.javasSE.banking.accountService.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.Currency;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {
    private String name;
    private Currency currency;
    private BigDecimal balance;
    private Integer clientId;


}
