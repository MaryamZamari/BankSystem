package com.javase.banking.facade.validation.accountvalidation;

import com.javase.banking.facade.validation.IValidation;
import com.javase.banking.model.account.Account;
import com.javase.banking.service.exception.ValidationException;

public class AccountValidation implements IValidation<Account> {
    @Override
    public void validate(Account account) throws ValidationException {

    }
}
