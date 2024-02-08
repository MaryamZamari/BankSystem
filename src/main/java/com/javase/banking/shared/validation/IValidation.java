package com.javase.banking.shared.validation;

import com.javase.banking.shared.exception.ValidationException;

@FunctionalInterface
public interface IValidation<T> {
    void validate(T t) throws ValidationException;

}
