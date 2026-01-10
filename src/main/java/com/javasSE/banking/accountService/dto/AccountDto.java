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
    private AmountDto balance;
    private Integer clientId;


}
