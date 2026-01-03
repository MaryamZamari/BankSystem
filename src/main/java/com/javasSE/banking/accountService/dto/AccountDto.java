package com.javasSE.banking.accountService.dto;

import com.javasSE.banking.accountService.model.AccountType;
import lombok.*;

import java.math.BigDecimal;
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {
    private String name;
    private AccountType currency;
    private BigDecimal balance;
    private Integer clientId;


}
