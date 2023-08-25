package br.com.jorge.habita.application.service.distribuicao.io;

public class DistribuicaoInput {
    private Integer qtdCasas;

    public DistribuicaoInput() {}

    public DistribuicaoInput(Integer qtdCasas) {
        this.qtdCasas = qtdCasas;
    }

    public Integer getQtdCasas() {
        return qtdCasas;
    }
}
