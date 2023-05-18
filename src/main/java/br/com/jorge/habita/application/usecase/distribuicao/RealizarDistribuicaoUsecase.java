package br.com.jorge.habita.application.usecase.distribuicao;

import br.com.jorge.habita.adapter.repository.FamiliaRepository;
import br.com.jorge.habita.adapter.repository.DistribuicaoRepository;
import br.com.jorge.habita.domain.entity.Familia;
import br.com.jorge.habita.domain.entity.Membro;
import lombok.Builder;
import org.springframework.data.domain.PageRequest;

import java.util.List;

@Builder
public class RealizarDistribuicaoUsecase {

    private DistribuicaoRepository distribuicaoRepository;
    private FamiliaRepository familiaRepository;

    public RealizarDistribuicaoOutput sortear() {

        // marcar a familia como contemplada
        List<Familia> familiasContempladas = familiaRepository.findByOrderByPontuacaoDesc(PageRequest.ofSize(3));

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
