package com.javase.banking.shared.validation;

import com.javase.banking.shared.exception.ValidationException;
import java.util.ArrayList;
import java.util.List;

public class ValidationContext<T> {
    /***
     * this class customises the implementation of the Strategy design pattern.
     */
    private List<IValidation<T>> validations;
    public ValidationContext(){
        this.validations = new ArrayList<>();
    }

    public void addValidationItem(IValidation<? extends T> validation){
        validations.add((IValidation<T>) validation);
    }

    public void validate(T object) throws ValidationException {
        Class<?> objectClass = object.getClass();

        for (IValidation<T> validation : validations) {
            if (isValidationApplicable(validation, objectClass)) {
                validation.validate(object);
            }
        }
    }

    private boolean isValidationApplicable(IValidation<T> validation, Class<?> objectClass) {
        Class<?> validationClass = validation.getClass();
        while (validationClass != null) {
            try {
                // Check if there is a method with a distinct parameter type for the current object class
                validationClass.getDeclaredMethod("validate", objectClass);
                return true;  // Validation is applicable
            } catch (NoSuchMethodException ignored) {
                // Validation method not found in the current class, try the superclass
                validationClass = validationClass.getSuperclass();
            }
        }
        return false;  // Validation not applicable
    }

}
