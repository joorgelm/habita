package br.com.jorge.habita.domain.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@SequenceGenerator(name = "seq_membro", sequenceName = "seq_membro_id", allocationSize = 1)
public class Membro {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_membro")
    private Long id;

    private String nome;

    private Integer idade;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "familia_id", referencedColumnName = "id")
    private Familia familia;
}
