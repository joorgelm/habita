package br.com.jorge.habita.application.service.familia.io;

import java.math.BigDecimal;
import java.util.List;

public class FamiliaInput {

    private BigDecimal rendaTotal;

    private List<Membro> membros;

    public void setRendaTotal(BigDecimal rendaTotal) {
        this.rendaTotal = rendaTotal;
    }

    public void setMembros(List<Membro> membros) {
        this.membros = membros;
    }

    public static class Membro {

        private String nome;

        private Integer idade;

        public Membro(String nome, Integer idade) {
            this.nome = nome;
            this.idade = idade;
        }

        public String getNome() {
            return nome;
        }

        public Integer getIdade() {
            return idade;
        }
    }

    public FamiliaInput() {
    }

    public FamiliaInput(BigDecimal rendaTotal, List<Membro> membros) {
        this.rendaTotal = rendaTotal;
        this.membros = membros;
    }

    public BigDecimal getRendaTotal() {
        return rendaTotal;
    }

    public List<Membro> getMembros() {
        return membros;
    }
}
