package br.edu.ifto.aula09.model.utils;

import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.ConstraintValidator;

public class CnpjValidator implements ConstraintValidator<ValidCNPJ, String> {

    private static final String CNPJ_REGEX = "\\d{14}|\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return false;
        }
        if (!value.matches(CNPJ_REGEX)) {
            return false;
        }
        return isValidCNPJ(value.replaceAll("\\D", ""));
    }

    private boolean isValidCNPJ(String cnpj) {
        if (cnpj.length() != 14 || cnpj.matches("(\\d)\\1{13}")) {
            return false;
        }
        try {
            int[] pesos1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
            int[] pesos2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

            int soma = 0;
            for (int i = 0; i < 12; i++) {
                soma += Character.getNumericValue(cnpj.charAt(i)) * pesos1[i];
            }
            int digito1 = soma % 11 < 2 ? 0 : 11 - (soma % 11);

            soma = 0;
            for (int i = 0; i < 13; i++) {
                soma += Character.getNumericValue(cnpj.charAt(i)) * pesos2[i];
            }
            int digito2 = soma % 11 < 2 ? 0 : 11 - (soma % 11);

            return digito1 == Character.getNumericValue(cnpj.charAt(12)) &&
                    digito2 == Character.getNumericValue(cnpj.charAt(13));
        } catch (Exception e) {
            return false;
        }
    }
}
