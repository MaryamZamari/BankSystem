package com.javasSE.banking.accountService.model;

import com.javasSE.banking.common.utility.IdGeneratorUtil;
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
    private String name;
    private String accountNumber;
    private AccountType type;
    private BigDecimal balance;
    private Integer clientId;
    private Boolean deleted;

    public Account(Integer id, String name, String accountNumber, AccountType type,
                   BigInteger balance, Integer clientId, Boolean deleted) {
        Id = IdGeneratorUtil.generateUniqueAccountId();
        this.name= name;
        this.accountNumber = accountNumber;
        this.type = type;
        this.balance = BigDecimal.ZERO;
        this.clientId = clientId;
        this.deleted = false;
    }
    @Override
    public boolean equals(Object obj) {
        return obj instanceof Account &&
                ((((Account) obj).getId())).equals(getId()) ||
                ((Account) obj).getName().equals((getName())) ||
                        ((Account) obj).getAccountNumber().equals(getAccountNumber());
   }

}


