package com.javase.banking.accountservice.validation;

import com.javase.banking.accountservice.dto.AccountDto;
import com.javase.banking.shared.validation.IValidation;
import com.javase.banking.shared.exception.ValidationException;
import java.math.BigDecimal;

public class BalanceValidation implements IValidation<AccountDto> {
    @Override
    public void validate(AccountDto account) throws ValidationException {
        BigDecimal balance= account.getBalance();
        boolean hasBalance= balance.compareTo(BigDecimal.ZERO) >= 0;
        if(!hasBalance){
            throw new ValidationException("Balance can not be less than zero");
        }
    }
}
