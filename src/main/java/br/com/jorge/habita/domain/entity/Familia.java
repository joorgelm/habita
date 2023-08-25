package br.com.jorge.habita.domain.entity;

import br.com.jorge.habita.application.service.familia.io.FamiliaInput;
import br.com.jorge.habita.application.service.familia.io.FamiliaOutput;
import br.com.jorge.habita.application.service.membro.io.MembroOutput;
import br.com.jorge.habita.domain.strategy.CriterioAvalicaoStrategy;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Entity
@SequenceGenerator(name = "seq_familia", sequenceName = "seq_familia_id", allocationSize = 1)
public class Familia implements DataValidation {

    private static final int IDADE_MAXIMA = 18;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_familia")
    private Long id;

    @Column(name = "renda_total", nullable = false)
    @NotNull
    @Positive
    private BigDecimal rendaTotal;

    @Positive
    @Column(name = "pontuacao")
    private Integer pontuacao;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "distribuicao_id", referencedColumnName = "id")
    private Distribuicao distribuicao;

    @OneToMany(mappedBy = "familia", fetch = FetchType.EAGER)
    private List<Membro> membros;

    @Column(name = "data_cadastro")
    @NotNull
    private LocalDateTime dataCadastro;

    public Familia() {}

    public Familia(BigDecimal rendaTotal) {
        this.rendaTotal = rendaTotal;
        this.dataCadastro = LocalDateTime.now();
        this.validarEstadoDoObjeto();
    }

    public static Familia of(FamiliaInput familiaInput) {
        return new Familia(familiaInput.getRendaTotal());
    }

    public long quantiadeDependentes() {
        return this.membros.stream().filter(membro -> membro.getIdade() < IDADE_MAXIMA).count();
    }

    public FamiliaOutput toOutput() {
        List<MembroOutput> membroOutputList = this.getMembros().stream()
                .map(Membro::toOutput)
                .toList();

        return new FamiliaOutput(this.getId(), membroOutputList);
    }

    public void atualizarPontuacao(List<CriterioAvalicaoStrategy> criterioAvalicaoStrategies) {
        this.pontuacao = criterioAvalicaoStrategies
                .stream()
                .map(criterioAvalicaoStrategy -> criterioAvalicaoStrategy.obterPontuacao(this))
                .reduce(0, Integer::sum);

        this.validarEstadoDoObjeto();
    }

    public void adicionaMembro(Membro membro) {
        List<Membro> membroList = Optional.ofNullable(this.membros)
                .orElse(List.of());

        this.membros = Stream
                .concat(membroList.stream(), Stream.of(membro))
                .toList();
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getRendaTotal() {
        return rendaTotal;
    }

    public Integer getPontuacao() {
        return pontuacao;
    }

    public Distribuicao getDistribuicao() {
        return distribuicao;
    }

    public List<Membro> getMembros() {
        return membros;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public void setDistribuicao(Distribuicao distribuicao) {
        this.distribuicao = distribuicao;
    }
}
