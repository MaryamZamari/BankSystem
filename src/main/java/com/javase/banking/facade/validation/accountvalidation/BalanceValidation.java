package com.javase.banking.facade.validation.accountvalidation;

import com.javase.banking.facade.validation.IValidation;
import com.javase.banking.model.account.Account;
import com.javase.banking.service.exception.ValidationException;
import java.math.BigDecimal;

public class BalanceValidation implements IValidation<Account> {
    @Override
    public void validate(Account account) throws ValidationException {
        BigDecimal balance= account.getBalance();
        boolean hasBalance= balance.compareTo(BigDecimal.ZERO) >= 0;
        if(!hasBalance){
            throw new ValidationException("Balance can not be less than zero");
        }
    }
}
