package br.com.jorge.habita.domain.entity;

import br.com.jorge.habita.application.service.distribuicao.io.DistribuicaoOutput;
import br.com.jorge.habita.application.service.familia.io.FamiliaOutput;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@SequenceGenerator(name = "seq_distribuicao", sequenceName = "seq_distribuicao_id", allocationSize = 1)
public class Distribuicao implements DataValidation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_distribuicao")
    private Long id;

    @Column(nullable = false)
    @NotNull
    private LocalDateTime distribuicaoData;

    public Distribuicao() {
        this.distribuicaoData = LocalDateTime.now();
        validarEstadoDoObjeto();
    }

    public Long getId() {
        return Long.valueOf(this.id);
    }

    public LocalDateTime getDistribuicaoData() {
        return distribuicaoData;
    }

    public DistribuicaoOutput toOutput(List<FamiliaOutput> familiaOutputList) {
        return new DistribuicaoOutput(
                this.getId(),
                this.getDistribuicaoData(),
                familiaOutputList
        );
    }
}
