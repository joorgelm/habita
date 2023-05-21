package br.com.jorge.habita.domain.exception;

public class RendaInvalidaException extends RuntimeException {
    public RendaInvalidaException() {
        super("Renda inválida");
    }
}
