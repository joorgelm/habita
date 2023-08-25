package br.com.jorge.habita.domain.strategy;

import br.com.jorge.habita.domain.entity.Familia;
import net.datafaker.Faker;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class RendaStrategyTest {

    private Faker faker;
    private RendaStrategy rendaStrategy;

    @Before
    public void setup() {
        rendaStrategy = new RendaStrategy();
        faker = new Faker();
    }

    @Test
    public void deveObterPontuacaoMaxima() {
        int pontuacaoCalculada = rendaStrategy.obterPontuacao(
                new Familia(RendaStrategy.RENDA_MINIMA)
        );
        Assert.assertEquals(RendaStrategy.PONTUACAO_MAXIMA, pontuacaoCalculada);
    }

    @Test
    public void deveObterPontuacaoMediaTetoRendaMaxima() {
        int pontuacaoCalculada = rendaStrategy.obterPontuacao(
                new Familia(RendaStrategy.RENDA_MAXIMA)
        );
        Assert.assertEquals(RendaStrategy.PONTUACAO_MEDIA, pontuacaoCalculada);
    }

    @Test
    public void deveObterPontuacaoMedia() {
        int pontuacaoCalculada = rendaStrategy.obterPontuacao(
                new Familia(BigDecimal.valueOf(faker.number().numberBetween(900, 1500)))
        );
        Assert.assertEquals(RendaStrategy.PONTUACAO_MEDIA, pontuacaoCalculada);
    }
    @Test
    public void deveObterPontuacaoMinima() {
        int pontuacaoCalculada = rendaStrategy.obterPontuacao(
                new Familia(BigDecimal.valueOf(faker.number().numberBetween(1501, 6000)))
        );
        Assert.assertEquals(RendaStrategy.PONTUACAO_MINIMA, pontuacaoCalculada);
    }
}
