package com.javasSE.banking.accountService.model;

import com.javasSE.banking.common.utility.IdGeneratorUtil;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Currency;

@Data
public class Account implements Serializable {
    @Setter(AccessLevel.NONE)
    private Integer id;
    private String name;
    private Amount balance;
    private Integer clientId;
    private Boolean deleted;

    public Account(String name, Amount balance, Integer clientId) {
        this.id = IdGeneratorUtil.generateUniqueAccountId();
        this.name = name;
        this.balance = balance;
        this.clientId = clientId;
        this.deleted = false;
    }

    public Account() { //noArgs constructor because MapStruct uses this by default.
        this.id = IdGeneratorUtil.generateUniqueAccountId();;
        this.deleted = false;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Account &&
                ((((Account) obj).getId())).equals(getId() );
   }



}


