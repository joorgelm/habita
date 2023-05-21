package br.com.jorge.habita.domain.entity;

import br.com.jorge.habita.domain.strategy.CriterioAvalicaoStrategy;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "seq_familia", sequenceName = "seq_familia_id", allocationSize = 1)
@Data
public class Familia {

    private static final int IDADE_MAXIMA = 18;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_familia")
    private Long id;

    @Column(name = "renda_total", nullable = false)
    private BigDecimal rendaTotal;

    @Column(name = "pontuacao")
    private Integer pontuacao;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "distribuicao_id", referencedColumnName = "id", nullable = true)
    private Distribuicao distribuicao;

    @OneToMany(mappedBy = "familia")
    private List<Membro> membros;

    public void atualizarPontuacao(List<CriterioAvalicaoStrategy> criterioAvalicaoStrategies) {
                this.setPontuacao(calcularPontuacao(criterioAvalicaoStrategies));
    }

    private Integer calcularPontuacao(List<CriterioAvalicaoStrategy> criterioAvalicaoStrategies) {
        return  criterioAvalicaoStrategies
                .stream()
                .map(criterioAvalicaoStrategy -> criterioAvalicaoStrategy.obterPontuacao(this))
                .reduce(0, Integer::sum);
    }
    public long quantiadeDependentes() {
        return this.membros.stream().filter(membro -> membro.getIdade() < IDADE_MAXIMA).count();
    }
}
