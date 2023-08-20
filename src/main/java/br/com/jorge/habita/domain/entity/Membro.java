package br.com.jorge.habita.domain.entity;

import br.com.jorge.habita.application.service.familia.io.FamiliaInput;
import br.com.jorge.habita.application.service.membro.io.MembroOutput;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Entity
@Getter
@SequenceGenerator(name = "seq_membro", sequenceName = "seq_membro_id", allocationSize = 1)
public class Membro implements DataValidation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_membro")
    private Long id;

    @NotEmpty
    private String nome;

    @Positive
    @NotNull
    private Integer idade;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "familia_id", referencedColumnName = "id")
    @NotNull
    private Familia familia;

    public Membro() {}

    public Membro(String nome, Integer idade, Familia familia) {
        this.nome = nome;
        this.idade = idade;
        this.familia = familia;
        validarEstadoDoObjeto();
    }

    public static Membro of(FamiliaInput.Membro membro, Familia familia) {
        return new Membro(
                membro.getNome(),
                membro.getIdade(),
                familia
        );
    }

    public MembroOutput toOutput() {
        return new MembroOutput(this.getNome());
    }
}
