package com.javasSE.banking.accountService.model;

import com.javasSE.banking.common.utility.IdGeneratorUtil;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class Account implements Serializable {
    @Setter(AccessLevel.NONE)
    private Integer Id;
    private AccountType currency;
    private BigDecimal balance;
    private Integer clientId;
    private Boolean deleted;

    public Account(Integer id, AccountType currency,
                   BigDecimal balance, Integer clientId, Boolean deleted) {
        Id = IdGeneratorUtil.generateUniqueAccountId();
        this.currency = currency;
        this.balance = BigDecimal.ZERO;
        this.clientId = clientId;
        this.deleted = false;
    }
    @Override
    public boolean equals(Object obj) {
        return obj instanceof Account &&
                ((((Account) obj).getId())).equals(getId() );
   }

}


