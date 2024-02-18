package com.javasSE.banking.accountService.model;

import com.javasSE.banking.common.utility.IdGeneratorUtil;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class Account implements Serializable {
    @Setter(AccessLevel.NONE)
    private Integer Id;
    private String accountNumber;
    private AccountType type;
    private BigDecimal balance;
    private Integer clientId;
    private Boolean deleted;

    public Account(Integer id, String accountNumber, AccountType type,
                   BigDecimal balance, Integer clientId, Boolean deleted) {
        Id = IdGeneratorUtil.generateUniqueAccountId();
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
                        ((Account) obj).getAccountNumber().equals(getAccountNumber());
   }

}


