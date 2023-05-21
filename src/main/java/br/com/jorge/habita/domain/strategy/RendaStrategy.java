package br.com.jorge.habita.domain.strategy;

import br.com.jorge.habita.domain.entity.Familia;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public class RendaStrategy  implements CriterioAvalicaoStrategy {

    public static final int PONTUACAO_MAXIMA = 5;
    public static final int PONTUACAO_MEDIA = 3;
    public static final int PONTUACAO_MINIMA= 0;

    public static final BigDecimal RENDA_MINIMA = new BigDecimal(900);
    public static final BigDecimal RENDA_MAXIMA = new BigDecimal(1500);

    @Override
    public int obterPontuacao(Familia familia) {
        BigDecimal rendaTotal = familia.getRendaTotal();

        if (rendaTotalMenorIgualRendaMinima(rendaTotal)) {
            return PONTUACAO_MAXIMA;
        } else if (rendaTotalMaiorQueRendaMaxima(rendaTotal)) {
            return PONTUACAO_MINIMA;
        }

        return PONTUACAO_MEDIA;
    }

    @Override
    public int pontuacaoMaxima() {
        return PONTUACAO_MAXIMA;
    }

    @Override
    public int pontuacaoMinima() {
        return PONTUACAO_MINIMA;
    }

    private static boolean rendaTotalMaiorQueRendaMaxima(BigDecimal rendaTotal) {
        return rendaTotal.compareTo(RENDA_MAXIMA) > 0;
    }

    private static boolean rendaTotalMenorIgualRendaMinima(BigDecimal rendaTotal) {
        return rendaTotal.compareTo(RENDA_MINIMA) <= 0;
    }
}
