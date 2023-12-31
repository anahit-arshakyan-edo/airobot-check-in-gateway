package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.validation;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.ValidationException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public final class Checker {
    private Checker() {
    }

    public static <T> void checkValidityWithJSR380(T obj) {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<T>> violations = validator.validate(obj);
            if (!violations.isEmpty()) {
                throw new ValidationException(violations.toString());
            }
        }
    }
}
