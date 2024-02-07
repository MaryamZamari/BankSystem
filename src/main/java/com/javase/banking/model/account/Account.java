package com.javase.banking.model.account;

import com.javase.banking.utility.IdGeneratorUtil;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
@Getter
@Setter
@ToString
public class Account implements Serializable {
    @Setter(AccessLevel.NONE)
    private Integer Id;
    private String accountNumber;
    private AccountType type;
    private BigDecimal balance;
    private Integer customerId;
    private Boolean deleted;

    public Account(Integer id, String accountNumber, AccountType type, BigInteger balance, Integer customerId, Boolean deleted) {
        Id = IdGeneratorUtil.generateUniqueClientId();
        this.accountNumber = accountNumber;
        this.type = type;
        this.balance = BigDecimal.ZERO;
        this.customerId = customerId;
        this.deleted = false;
    }
}


