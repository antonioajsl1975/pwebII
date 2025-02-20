package br.edu.ifto.aula09.model.utils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CEPValidator implements ConstraintValidator<ValidCEP, String> {

    private static final String CEP_REGEX = "\\d{5}-\\d{3}";

    @Override
    public void initialize(ValidCEP constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return false;
        }
        return value.matches(CEP_REGEX);
    }
}
