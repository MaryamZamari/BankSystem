package com.javase.banking.facade.validation.accountvalidation;

import com.javase.banking.facade.validation.ValidationContext;
import com.javase.banking.model.account.Account;

public class AccountValidationContext extends ValidationContext<Account> {
    public AccountValidationContext(){
        new BalanceValidation();
    }
}
