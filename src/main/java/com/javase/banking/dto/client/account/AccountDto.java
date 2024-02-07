package com.javase.banking.dto.client.account;

import com.javase.banking.model.account.AccountType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigInteger;
@Getter
@Setter
@ToString
public class AccountDto {
    private Integer Id;
    private String accountNumber;
    private AccountType type;
    private BigInteger balance;
    private Integer customerId;

}
