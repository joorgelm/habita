package br.com.jorge.habita.domain.strategy;

import br.com.jorge.habita.domain.entity.Familia;
import lombok.Builder;

@Builder
public class DependentesStrategy implements CriterioAvalicaoStrategy {

    public static final int PONTUACAO_MAXIMA = 3;
    public static final int PONTUACAO_MEDIA = 2;
    public static final int PONTUACAO_MINIMA= 0;

    public static final int TETO_DEPENDENTES = 3;
    public static final int PISO_DEPENDENTES = 1;
    @Override
    public int obterPontuacao(Familia familia) {

        long quantidadeDependentes = familia.quantiadeDependentes();

        if (quantidadeDependentesMaiorIgualTetoDependentes(quantidadeDependentes))
            return PONTUACAO_MAXIMA;

        if (quantidadeDependentesMaiorIgualPisoDependentes(quantidadeDependentes))
            return PONTUACAO_MEDIA;

        return PONTUACAO_MINIMA;
    }

    @Override
    public int pontuacaoMaxima() {
        return PONTUACAO_MAXIMA;
    }

    @Override
    public int pontuacaoMinima() {
        return PONTUACAO_MINIMA;
    }

    private static boolean quantidadeDependentesMaiorIgualPisoDependentes(long quantidadeDependentes) {
        return quantidadeDependentes >= PISO_DEPENDENTES;
    }

    private static boolean quantidadeDependentesMaiorIgualTetoDependentes(long quantidadeDependentes) {
        return quantidadeDependentes >= TETO_DEPENDENTES;
    }
}
