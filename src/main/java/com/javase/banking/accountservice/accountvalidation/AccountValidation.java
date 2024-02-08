package com.javase.banking.accountservice.accountvalidation;

import com.javase.banking.accountservice.dto.AccountDto;
import com.javase.banking.shared.validation.IValidation;
import com.javase.banking.shared.exception.ValidationException;

public class AccountValidation implements IValidation<AccountDto> {
    @Override
    public void validate(AccountDto account) throws ValidationException {

    }
}
