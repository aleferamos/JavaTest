package br.com.cd2tec.calculafrete.exceptions;

public class RegraDeNegocioException extends RuntimeException{

    public RegraDeNegocioException() {
        super("erro.naoEncontrado");
    }

    public RegraDeNegocioException(String mensagem) {
        super(mensagem);
    }
}
