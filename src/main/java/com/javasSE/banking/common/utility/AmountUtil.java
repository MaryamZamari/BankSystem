package com.javasSE.banking.common.utility;

import com.javasSE.banking.accountService.model.Amount;

import java.math.BigDecimal;

public class AmountUtil {
    // 1. The private static instance
    private static final AmountUtil INSTANCE = new AmountUtil();
    // 2. PRIVATE constructor
    private AmountUtil() { }

    // 3. Global access point
    public static AmountUtil getInstance() {
        return INSTANCE;
    }

    public static Amount add(Amount firstAmount , Amount secondAmount){
        if(firstAmount.getCurrency() != secondAmount.getCurrency()){
            throw new RuntimeException("Currencies don't match!");
            }
        return new Amount(firstAmount.getCurrency() ,
                firstAmount.getValue().add(secondAmount.getValue()));
    }

    public static Amount subtract(Amount firstAmount , Amount secondAmount){
        if(firstAmount.getCurrency() != secondAmount.getCurrency()){
            throw new RuntimeException("Currencies don't match!");
        }
        return new Amount(firstAmount.getCurrency() ,
                firstAmount.getValue().subtract(secondAmount.getValue()));
    }

    public static boolean compareTo(Amount sourceAmount , Amount amountToSubtract){
        if(sourceAmount.getCurrency() != sourceAmount.getCurrency()){
            throw new RuntimeException("Currencies don't match!");
        }
        return (sourceAmount.getValue().compareTo(BigDecimal.ZERO) > 0) &&
                (sourceAmount.getValue().compareTo(amountToSubtract.getValue()) >= 0);
    }

}
