package br.com.jorge.habita.domain.strategy;

import br.com.jorge.habita.domain.entity.Familia;
import lombok.Builder;

@Builder
public class DependentesStrategy implements CriterioAvalicaoStrategy {
    @Override
    public int obterPontuacao(Familia familia) {

        long quantidadeDependentes = familia.quantiadeDependentes();

        if (quantidadeDependentes > 2) return 3;

        if (quantidadeDependentes < 3 && quantidadeDependentes > 0) return  2;

        return 0;
    }
}
