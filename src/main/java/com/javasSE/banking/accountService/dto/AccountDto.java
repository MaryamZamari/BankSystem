package com.javasSE.banking.accountService.dto;

import com.javasSE.banking.accountService.model.AccountType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.math.BigDecimal;
@Getter
@Setter
@ToString
@AllArgsConstructor
public class AccountDto {
    private Integer Id;
    private String name;
    private String accountNumber;
    private AccountType type;
    private BigDecimal balance;
    private Integer clientId;


}
