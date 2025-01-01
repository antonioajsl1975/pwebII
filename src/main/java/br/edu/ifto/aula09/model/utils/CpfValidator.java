package br.edu.ifto.aula09.model.utils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CpfValidator implements ConstraintValidator<ValidCPF, String> {

    private static final String CPF_REGEX = "\\d{11}|\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return false; // CPF não pode ser nulo ou vazio
        }
        if (!value.matches(CPF_REGEX)) {
            return false; // Formato do CPF inválido
        }
        return isValidCPF(value.replaceAll("\\D", "")); // Remove caracteres não numéricos antes da validação lógica
    }

    private boolean isValidCPF(String cpf) {
        if (cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")) {
            return false; // Tamanho incorreto ou todos os dígitos iguais
        }
        try {
            int soma = 0;
            for (int i = 0; i < 9; i++) {
                soma += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
            }
            int digito1 = 11 - (soma % 11);
            if (digito1 >= 10) digito1 = 0;

            soma = 0;
            for (int i = 0; i < 10; i++) {
                soma += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
            }
            int digito2 = 11 - (soma % 11);
            if (digito2 >= 10) digito2 = 0;

            return digito1 == Character.getNumericValue(cpf.charAt(9)) &&
                    digito2 == Character.getNumericValue(cpf.charAt(10));
        } catch (Exception e) {
            return false;
        }
    }
}

