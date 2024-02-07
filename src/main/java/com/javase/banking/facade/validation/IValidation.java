package com.javase.banking.facade.validation;

import com.javase.banking.service.exception.ValidationException;

@FunctionalInterface
public interface IValidation<T> {
    void validate(T t) throws ValidationException;

}
