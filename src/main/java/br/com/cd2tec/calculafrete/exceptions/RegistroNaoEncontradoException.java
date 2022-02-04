package br.com.cd2tec.calculafrete.exceptions;

public class RegistroNaoEncontradoException extends RuntimeException{

    public RegistroNaoEncontradoException() {
        super("erro.naoEncontrado");
    }

    public RegistroNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
