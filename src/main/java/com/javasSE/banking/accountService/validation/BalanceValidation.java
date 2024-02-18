package com.javasSE.banking.accountService.validation;

import com.javasSE.banking.accountService.dto.AccountDto;
import com.javasSE.banking.common.validation.IValidation;
import com.javasSE.banking.common.exception.ValidationException;
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
