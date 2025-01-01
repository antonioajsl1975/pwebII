package br.edu.ifto.aula09.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public String handleConstraintViolationException(DataIntegrityViolationException ex, RedirectAttributes redirectAttributes) {
        String message = ex.getCause() != null ? ex.getCause().getMessage() : ex.getMessage();

        if (message.contains("uc_pessoa_juridica__cnpj")) {
            redirectAttributes.addFlashAttribute("error", "O CNPJ informado já está cadastrado.");
        } else if (message.contains("uc_pessoafisica__cpf")) {
            redirectAttributes.addFlashAttribute("error", "O CPF informado já está cadastrado.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Erro ao salvar os dados. Por favor, tente novamente.");
        }
        return "pessoafisica/form"; // Redireciona para o formulário
    }
}
