package br.edu.ifto.aula09.exception;

public class UsuarioNaoAutenticadoException extends RuntimeException {
    public UsuarioNaoAutenticadoException(String mensagem) {
        super(mensagem);
    }
}
