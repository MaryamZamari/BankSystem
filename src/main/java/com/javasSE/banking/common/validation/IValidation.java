package com.javasSE.banking.common.validation;

import com.javasSE.banking.common.exception.ValidationException;

@FunctionalInterface
public interface IValidation<T> {
    void validate(T t) throws ValidationException;

}
