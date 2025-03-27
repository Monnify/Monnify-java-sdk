package com.monnify.utils;

import com.monnify.exceptions.MonnifyValidationException;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.ConstraintViolation;
import java.util.Set;

public class ValidationUtil {
    private static final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private static final Validator validator = factory.getValidator();

    public static <T> void validate(T requestObject) {
        Set<ConstraintViolation<T>> violations = validator.validate(requestObject);

        if (!violations.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder("Validation errors:");
            violations.forEach(violation ->
                    errorMessage.append("\n").append(violation.getMessage()));
            throw new MonnifyValidationException(errorMessage.toString());
        }
    }
}
