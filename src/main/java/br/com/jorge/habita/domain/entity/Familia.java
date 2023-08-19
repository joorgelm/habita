package br.com.jorge.habita.domain.entity;

import br.com.jorge.habita.domain.DataValidation;
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
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Entity
@SequenceGenerator(name = "seq_familia", sequenceName = "seq_familia_id", allocationSize = 1)
public class Familia implements DataValidation {

    private static final int IDADE_MAXIMA_DEPENDENTES = 18;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_familia")
    private Long id;

    @Column(name = "renda_total", nullable = false)
    @PositiveOrZero
    @NotNull
    private BigDecimal renda;

    @Column(name = "pontuacao", nullable = true)
    private Integer pontuacao;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "distribuicao_id", referencedColumnName = "id", nullable = true)
    private Distribuicao distribuicao;

    @OneToMany(mappedBy = "familia")
    private List<Membro> membros;

    @Column(name = "data_cadastro")
    @NotNull
    private LocalDateTime dataCadastro;

    public Long getId() {
        return Long.valueOf(id.toString()); // todo validar se a referencia é diferente
    }

    public BigDecimal getRenda() {
        return BigDecimal.valueOf(renda.doubleValue());
    }

    public Integer getPontuacao() {
        return Integer.valueOf(pontuacao.toString());
    }

    public Distribuicao getDistribuicao() {
        return distribuicao;
    }

    public List<Membro> getMembros() {
        return Collections.unmodifiableList(membros);
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public long quantiadeDependentes() {
        return this.membros.stream()
                .filter(membro -> membro.getIdade() < IDADE_MAXIMA_DEPENDENTES)
                .count();
    }

    public void adicionaMembro(Membro membro) {
        List.copyOf(this.membros).add(membro);

        this.membros = List.copyOf(this.membros).add(membro);
//        this.membros.add(membro);
    }

    public Familia() {}

    public Familia(Long id, BigDecimal renda) {
        this.id = id;
        this.renda = renda;
        this.membros = List.of();
        this.dataCadastro = LocalDateTime.now();

        this.validarEstadoDoObjeto(this);
    }

    public Familia(Long id, BigDecimal renda, Integer pontuacao, Distribuicao distribuicao, LocalDateTime dataCadastro) {
        this.id = id;
        this.renda = renda;
        this.pontuacao = pontuacao;
        this.distribuicao = distribuicao;
        this.dataCadastro = dataCadastro;

        this.validarEstadoDoObjeto(this);
    }

    public Familia(Long id,
                   BigDecimal renda,
                   Integer pontuacao,
                   Distribuicao distribuicao,
                   List<Membro> membros,
                   LocalDateTime dataCadastro
    ) {
        this.id = id;
        this.renda = renda;
        this.pontuacao = pontuacao;
        this.distribuicao = distribuicao;
        this.membros = membros;
        this.dataCadastro = dataCadastro;

        this.validarEstadoDoObjeto(this);
    }
}
