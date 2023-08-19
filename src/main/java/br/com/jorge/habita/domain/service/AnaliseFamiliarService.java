package br.com.jorge.habita.domain.service;

import br.com.jorge.habita.domain.entity.Familia;
import br.com.jorge.habita.domain.strategy.CriterioAvalicaoStrategy;
import lombok.Builder;

import java.util.List;

@Builder
public class AnaliseFamiliarService {
    private List<CriterioAvalicaoStrategy> criterioAvalicaoStrategies;

    public void atualizarPontuacao(Familia familia) {
//        familia.setPontuacao(
//                criterioAvalicaoStrategies
//                        .stream()
//                        .map(criterioAvalicaoStrategy -> calcularPontuacao(familia, criterioAvalicaoStrategy))
//                        .reduce(0, Integer::sum)
//        );
    }

    private Integer calcularPontuacao(Familia familia, CriterioAvalicaoStrategy criterioAvalicaoStrategies) {
        return  criterioAvalicaoStrategies.obterPontuacao(familia);
    }
}
