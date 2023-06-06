package br.com.jorge.habita.application.repository;

import br.com.jorge.habita.domain.entity.Distribuicao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistribuicaoRepository  extends JpaRepository<Distribuicao, Long> {
}
