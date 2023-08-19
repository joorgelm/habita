package br.com.jorge.habita.domain.entity;

import br.com.jorge.habita.application.usecase.familia.cadastrar.CadastrarFamiliaInput;
import br.com.jorge.habita.domain.DataValidation;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@SequenceGenerator(name = "seq_membro", sequenceName = "seq_membro_id", allocationSize = 1)
public class Membro implements DataValidation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_membro")
    private Long id;

    @NotNull
    @NotEmpty
    private String nome;

    @Positive
    private Integer idade;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "familia_id", referencedColumnName = "id")
    private Familia familia;

    public Membro() {}

    public Membro(String nome, Integer idade) {
        this.nome = nome;
        this.idade = idade;
        this.validarEstadoDoObjeto(this);
    }

    public Membro(String nome, Integer idade, Familia familia) {
        this.nome = nome;
        this.idade = idade;
        this.familia = familia;
        this.validarEstadoDoObjeto(this);
    }
    public Membro(Long id, String nome, Integer idade, Familia familia) {
        this.id = id;
        this.nome = nome;
        this.idade = idade;
        this.familia = familia;
        this.validarEstadoDoObjeto(this);
    }

    public static Membro of(@Valid CadastrarFamiliaInput.Membro input, Familia familia) {
        return new Membro(
                input.getNome(),
                input.getIdade(),
                familia
        );
    }

    public Long getId() {
        return Long.valueOf(id);
    }

    public String getNome() {
        return String.valueOf(nome);
    }

    public Integer getIdade() {
        return Integer.valueOf(idade);
    }

    public Familia getFamilia() { //todo: criar uma copia
        return familia;
    }
}
