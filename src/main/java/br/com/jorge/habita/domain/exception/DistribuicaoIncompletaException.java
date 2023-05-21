package br.com.jorge.habita.domain.exception;

public class DistribuicaoIncompletaException extends RuntimeException{

    public DistribuicaoIncompletaException() {
        super("O número de famílias solicitado é maior do que o número de famílias elegíveis cadastradas.");
    }
}
