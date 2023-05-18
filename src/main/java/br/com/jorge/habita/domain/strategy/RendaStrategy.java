package br.com.jorge.habita.domain.strategy;

import br.com.jorge.habita.domain.entity.Familia;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public class RendaStrategy  implements CriterioAvalicaoStrategy {

    private static final BigDecimal RENDA_MINIMA = new BigDecimal(900);

    private static final BigDecimal RENDA_MAXIMA = new BigDecimal(1500);
    @Override
    public int obterPontuacao(Familia familia) {
        BigDecimal rendaTotal = familia.getRendaTotal();

        if (rendaTotal.compareTo(RENDA_MINIMA) <= 0) {
            return 5;
        } else if (rendaTotal.compareTo(RENDA_MAXIMA) > 0) {
            return 0;
        }

        return 3;
    }
}
