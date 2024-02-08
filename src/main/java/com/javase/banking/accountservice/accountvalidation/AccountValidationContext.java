package com.javase.banking.accountservice.accountvalidation;

import com.javase.banking.accountservice.dto.AccountDto;
import com.javase.banking.shared.validation.ValidationContext;

public class AccountValidationContext extends ValidationContext<AccountDto> {
    public AccountValidationContext(){
        new BalanceValidation();
    }
}
