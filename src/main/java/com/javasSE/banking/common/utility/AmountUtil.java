package com.javasSE.banking.common.utility;

import com.javasSE.banking.accountService.model.Account;
import com.javasSE.banking.accountService.model.Amount;

public class AmountUtil {
    public static Amount add(Amount firstAmount , Amount secondAmount){
        if(firstAmount.getCurrency() != secondAmount.getCurrency()){
            throw new RuntimeException("Currencies don't match!");
            }
        return new Amount(firstAmount.getCurrency() ,
                firstAmount.getValue().add(secondAmount.getValue()));
    }

}
