package br.com.jorge.habita.application.usecase.distribuicao;

import br.com.jorge.habita.adapter.repository.DistribuicaoRepository;
import br.com.jorge.habita.adapter.repository.FamiliaRepository;
import br.com.jorge.habita.domain.entity.Distribuicao;
import br.com.jorge.habita.domain.entity.Familia;
import br.com.jorge.habita.domain.entity.Membro;
import br.com.jorge.habita.domain.exceptio.DistribuicaoVaziaException;
import jakarta.transaction.Transactional;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public class RealizarDistribuicaoUsecase {

    private DistribuicaoRepository distribuicaoRepository;
    private FamiliaRepository familiaRepository;

    @Transactional
    public RealizarDistribuicaoOutput sortear(Integer quantidadeCasas) {
        List<Familia> familiasContempladas = familiaRepository.findByDistribuicaoIsNullOrderByPontuacaoDesc(quantidadeCasas);

        if (familiasContempladas.isEmpty())
            throw new DistribuicaoVaziaException();

        Distribuicao distribuicao = Distribuicao
                .builder()
                .distribuicaoData(LocalDateTime.now())
                .build();

        Distribuicao save = distribuicaoRepository.save(distribuicao);

        familiasContempladas.forEach(familia -> {
            familia.setDistribuicao(save);
        });

        familiaRepository.saveAll(familiasContempladas);

        List<RealizarDistribuicaoOutput.RealizarDistribuicaoFamiliaOutput> familiasContempladasOutput = familiasContempladas
                .stream()
                .map(RealizarDistribuicaoUsecase::familiaOutputConverter)
                .toList();

        return RealizarDistribuicaoOutput.
                builder()
                .familiasContempladas(familiasContempladasOutput)
                .build();
    }

    private static RealizarDistribuicaoOutput.RealizarDistribuicaoFamiliaOutput familiaOutputConverter(Familia familia) {
        List<RealizarDistribuicaoOutput.RealizarDistribuicaoMembroOutput> membrosOutput = familia.getMembros()
                .stream()
                .map(RealizarDistribuicaoUsecase::membroOutputConverter)
                .toList();

        return RealizarDistribuicaoOutput.RealizarDistribuicaoFamiliaOutput
                .builder()
                .id(familia.getId())
                .membros(membrosOutput)
                .build();
    }

    private static RealizarDistribuicaoOutput.RealizarDistribuicaoMembroOutput membroOutputConverter(Membro membro) {
        return RealizarDistribuicaoOutput.RealizarDistribuicaoMembroOutput
                .builder()
                .nome(membro.getNome())
                .build();
    }
}
