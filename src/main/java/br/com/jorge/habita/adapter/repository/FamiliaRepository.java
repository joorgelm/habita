package br.com.jorge.habita.adapter.repository;

import br.com.jorge.habita.domain.entity.Familia;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FamiliaRepository extends JpaRepository<Familia, Long> {

        List<Familia> findByOrderByPontuacaoDesc(PageRequest pageRequest);
}
