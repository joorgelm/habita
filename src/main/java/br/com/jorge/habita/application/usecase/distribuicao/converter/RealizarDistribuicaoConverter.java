package br.com.jorge.habita.application.usecase.distribuicao.converter;

import br.com.jorge.habita.application.usecase.distribuicao.RealizarDistribuicaoOutput;
import br.com.jorge.habita.domain.entity.Familia;
import br.com.jorge.habita.domain.entity.Membro;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class RealizarDistribuicaoConverter {

    public static RealizarDistribuicaoOutput converter(List<Familia> familias) {
        List<RealizarDistribuicaoOutput.RealizarDistribuicaoFamiliaOutput> familiaOutputList = familias
                .stream()
                .map(RealizarDistribuicaoConverter::familiaOutputConverter)
                .toList();

        return RealizarDistribuicaoOutput.
                builder()
                .familiasContempladas(familiaOutputList)
                .build();
    }

    private static RealizarDistribuicaoOutput.RealizarDistribuicaoFamiliaOutput familiaOutputConverter(Familia familia) {
        List<RealizarDistribuicaoOutput.RealizarDistribuicaoMembroOutput> membrosOutput = familia.getMembros()
                .stream()
                .map(RealizarDistribuicaoConverter::membroOutputConverter)
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
