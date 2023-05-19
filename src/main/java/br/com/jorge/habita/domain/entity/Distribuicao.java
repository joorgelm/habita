package br.com.jorge.habita.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "seq_distribuicao", sequenceName = "seq_distribuicao_id", allocationSize = 1)
@Data
public class Distribuicao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_distribuicao")
    private Long id;

    @Column(nullable = false)
    private LocalDateTime distribuicaoData;

    @OneToMany(mappedBy = "distribuicao")
    private List<Familia> familiasContempladas;
}
