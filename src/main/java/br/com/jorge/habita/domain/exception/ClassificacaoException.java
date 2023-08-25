package br.com.jorge.habita.domain.exception;

public class ClassificacaoException extends RuntimeException {

    public ClassificacaoException() {
        super("Falha ao atualizar pontuação das familias cadastradas.");
    }
}
