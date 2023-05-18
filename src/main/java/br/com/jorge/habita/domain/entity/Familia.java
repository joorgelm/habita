package br.com.jorge.habita.domain.entity;

import jakarta.persistence.*;
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
@Data
public class Familia {

    private static final int IDADE_MAXIMA = 18;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //todo: colocar notnull e valor defaul para os campos
    private BigDecimal rendaTotal;

    private Integer pontuacao;

    @OneToMany(mappedBy = "familia")
    private List<Membro> membros;
    public long quantiadeDependentes() {
        return this.membros.stream().filter(membro -> membro.getIdade() < IDADE_MAXIMA).count();
    }
}
