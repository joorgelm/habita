package br.com.jorge.habita.application.usecase.distribuicao;

import br.com.jorge.habita.adapter.repository.DistribuicaoRepository;
import br.com.jorge.habita.adapter.repository.FamiliaRepository;
import br.com.jorge.habita.application.usecase.distribuicao.converter.RealizarDistribuicaoConverter;
import br.com.jorge.habita.domain.entity.Distribuicao;
import br.com.jorge.habita.domain.entity.Familia;
import br.com.jorge.habita.domain.exception.DistribuicaoIncompletaException;
import br.com.jorge.habita.domain.strategy.CriterioAvalicaoStrategy;
import jakarta.transaction.Transactional;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public class RealizarDistribuicaoUsecase {

    private DistribuicaoRepository distribuicaoRepository;
    private FamiliaRepository familiaRepository;
    private List<CriterioAvalicaoStrategy> criterioAvalicaoStrategies;

    @Transactional
    public RealizarDistribuicaoOutput realizarDistribuicao(Integer quantidadeCasas) {
        classificarFamilias();
        List<Familia> familiasContempladas = buscarFamiliasContempladas(quantidadeCasas);
        vincularDistribuicao(familiasContempladas);

        return RealizarDistribuicaoConverter.converter(familiasContempladas);
    }

    private void vincularDistribuicao(List<Familia> familiasContempladas) {
        familiasContempladas.forEach(familia -> {
            familia.setDistribuicao(gerarRegistroDeDistribuicao());
        });
        familiaRepository.saveAll(familiasContempladas);
    }

    private Distribuicao gerarRegistroDeDistribuicao() {
        Distribuicao distribuicao = Distribuicao
                .builder()
                .distribuicaoData(LocalDateTime.now())
                .build();

        return distribuicaoRepository.save(distribuicao);
    }

    private List<Familia> buscarFamiliasContempladas(Integer quantidadeCasas) {
        List<Familia> familiasContempladas = familiaRepository.findByDistribuicaoIsNullOrderByPontuacaoDesc(quantidadeCasas);

        if (familiasContempladas.size() < quantidadeCasas)
            throw new DistribuicaoIncompletaException();

        return familiasContempladas;
    }
    private void classificarFamilias() {
        familiaRepository.saveAll(
                familiaRepository.findAll()
                        .stream()
                        .peek(familia -> familia.atualizarPontuacao(criterioAvalicaoStrategies))
                        .toList()
        );
    }
}
