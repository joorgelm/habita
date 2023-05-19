package br.com.jorge.habita.adapter.repository;

import br.com.jorge.habita.domain.entity.Familia;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FamiliaRepository extends JpaRepository<Familia, Long> {

/*        @Query(
                nativeQuery = true,
                value = "SELECT * FROM Familia f WHERE f.distribuicao_id IS NULL ORDER BY f.pontuacao DESC LIMIT :qtd"
        )
        List<Familia> findByOrderByPontuacaoDesc(@Param("qtd") Integer qtd);*/

//        List<Familia> findByDistribuicaoIsNullAndOrderByPontuacaoDesc();

        List<Familia> findByDistribuicaoIsNullOrderByPontuacaoDesc(PageRequest pageRequest);

        default List<Familia> findByDistribuicaoIsNullOrderByPontuacaoDesc(Integer qtd) {
                return findByDistribuicaoIsNullOrderByPontuacaoDesc(PageRequest.of(0, qtd));
        }
}
