package com.javasSE.banking.common.validation;

import com.javasSE.banking.common.exception.ValidationException;
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
            System.out.println("Validation class: " + validationClass.getName());
            System.out.println("Object class: " + objectClass.getName());
            // Check if the validation class is the same as or a superclass of the object class
            if (validationClass.isAssignableFrom(objectClass) || validationClass.isAssignableFrom(objectClass.getSuperclass())) {
                // If the object class is a subclass of the validation class,
                // only apply the validation if they are exactly the same class
                if (!objectClass.equals(validationClass)) {
                    System.out.println("Skipping validation");
                    return false; // Skip validation
                }
                System.out.println("Validation applicable");
                return true; // Validation is applicable
            }
            validationClass = validationClass.getSuperclass();
        }
        System.out.println("Validation not applicable");
        return false;  // Validation not applicable
    }

}
