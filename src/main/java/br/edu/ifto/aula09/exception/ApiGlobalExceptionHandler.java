package br.edu.ifto.aula09.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ApiGlobalExceptionHandler {

    @ExceptionHandler(UsuarioNaoAutenticadoException.class)
    public ModelAndView handleUsuarioNaoAutenticadoException(UsuarioNaoAutenticadoException ex) {

        return new ModelAndView("redirect:/login");
    }

    @ExceptionHandler(IllegalStateException.class)
    public ModelAndView handleIllegalStateException(IllegalStateException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return new ModelAndView("venda/carrinho");
    }
}
