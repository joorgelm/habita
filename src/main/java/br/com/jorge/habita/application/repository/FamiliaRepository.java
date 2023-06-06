package br.com.jorge.habita.application.repository;

import br.com.jorge.habita.domain.entity.Familia;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FamiliaRepository extends JpaRepository<Familia, Long> {
        List<Familia> findByDistribuicaoIsNullOrderByPontuacaoDesc(PageRequest pageRequest);

        default List<Familia> findByDistribuicaoIsNullOrderByPontuacaoDesc(Integer qtd) {
                return findByDistribuicaoIsNullOrderByPontuacaoDesc(PageRequest.of(0, qtd));
        }
}
