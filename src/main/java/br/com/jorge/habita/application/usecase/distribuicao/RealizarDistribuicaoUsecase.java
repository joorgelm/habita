package br.com.jorge.habita.application.usecase.distribuicao;

import br.com.jorge.habita.application.repository.DistribuicaoRepository;
import br.com.jorge.habita.application.repository.FamiliaRepository;
import br.com.jorge.habita.application.usecase.distribuicao.converter.RealizarDistribuicaoConverter;
import br.com.jorge.habita.domain.entity.Distribuicao;
import br.com.jorge.habita.domain.entity.Familia;
import br.com.jorge.habita.domain.exception.DistribuicaoIncompletaException;
import br.com.jorge.habita.domain.service.AnaliseFamiliarService;
import br.com.jorge.habita.domain.service.DistribuicaoService;
import jakarta.transaction.Transactional;
import lombok.Builder;

import java.util.List;

@Builder
public class RealizarDistribuicaoUsecase {

    private FamiliaRepository familiaRepository;
    private DistribuicaoRepository distribuicaoRepository;
    private AnaliseFamiliarService analiseFamiliarService;
    private DistribuicaoService distribuicaoService;

    @Transactional
    public RealizarDistribuicaoOutput realizarDistribuicao(RealizarDistribuicaoInput input) {
        atualizarPontuacaoDasFamiliasCadastradas();

        List<Familia> familiasContempladas = buscarFamiliasContempladas(input.getQtdCasas());
        vincularDistribuicao(familiasContempladas);

        return RealizarDistribuicaoConverter.converter(familiasContempladas);
    }

    private void vincularDistribuicao(List<Familia> familiasContempladas) {
        Distribuicao distribuicao = gerarRegistroDeDistribuicao();
        familiaRepository.saveAll(distribuicaoService.homologarDistribuicao(familiasContempladas, distribuicao));
    }

    private Distribuicao gerarRegistroDeDistribuicao() {
        return distribuicaoRepository.save(distribuicaoService.gerarRegistroDeDistribuicao());
    }

    private List<Familia> buscarFamiliasContempladas(Integer quantidadeCasas) {
        List<Familia> familiasContempladas = familiaRepository.findByDistribuicaoIsNullOrderByPontuacaoDesc(quantidadeCasas);

        if (familiasContempladas.size() < quantidadeCasas)
            throw new DistribuicaoIncompletaException();

        return familiasContempladas;
    }
    private void atualizarPontuacaoDasFamiliasCadastradas() {
        familiaRepository.saveAll(
                familiaRepository.findAll()
                        .stream()
                        .peek(familia -> analiseFamiliarService.atualizarPontuacao(familia)) //TODO: REMOVER PEEK
                        .toList()
        );
    }
}
