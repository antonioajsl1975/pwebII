package br.edu.ifto.aula09.model.utils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TelefoneValidator implements ConstraintValidator<ValidTelefone, String> {

    private static final String TELEFONE_REGEX = "\\(\\d{2}\\)\\s\\d{4,5}-\\d{4}|\\d{10,11}";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return false; // Telefone não pode ser nulo ou vazio
        }
        if (!value.matches(TELEFONE_REGEX)) {
            return false; // Formato do telefone inválido
        }
        return isValidTelefone(value.replaceAll("\\D", "")); // Remove caracteres não numéricos antes da validação lógica
    }

    private boolean isValidTelefone(String telefone) {
        return telefone.length() == 10 || telefone.length() == 11; // Verifica se tem 10 ou 11 dígitos
    }
}

