package br.com.jorge.habita.domain.strategy;

import br.com.jorge.habita.domain.entity.Familia;

public interface CriterioAvalicaoStrategy {

    int obterPontuacao(Familia familia);
}
