package br.com.jorge.habita.domain.exceptio;

public class DistribuicaoVaziaException extends RuntimeException{

    public DistribuicaoVaziaException() {
        super("Não existem famílias elegíveis para a distribuição");
    }
}
