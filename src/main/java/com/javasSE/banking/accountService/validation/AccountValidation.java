package com.javasSE.banking.accountService.validation;

import com.javasSE.banking.accountService.dto.AccountDto;
import com.javasSE.banking.common.validation.IValidation;
import com.javasSE.banking.common.exception.ValidationException;

public class AccountValidation implements IValidation<AccountDto> {
    @Override
    public void validate(AccountDto account) throws ValidationException {

    }
}
