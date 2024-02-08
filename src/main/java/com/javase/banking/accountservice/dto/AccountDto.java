package com.javase.banking.accountservice.dto;

import com.javase.banking.accountservice.model.AccountType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.math.BigDecimal;
@Getter
@Setter
@ToString
public class AccountDto {
    private Integer Id;
    private String accountNumber;
    private AccountType type;
    private BigDecimal balance;
    private Integer customerId;


}
