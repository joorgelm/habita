package br.com.jorge.habita.domain;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

import java.util.Optional;
import java.util.Set;

public interface DataValidation {

    default void validarEstadoDoObjeto(DataValidation targetValidation) {
        Optional.ofNullable(targetValidation).orElseThrow(IllegalStateException::new);

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<DataValidation>> constraintViolations = validator.validate(targetValidation);

        if (constraintViolations.isEmpty()) return;

        ConstraintViolation<DataValidation> violation = constraintViolations.iterator().next();
        throw new IllegalStateException(
                violation.getPropertyPath() + " " + violation.getMessage()
        );
    }
}
