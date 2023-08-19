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
        rendaStrategy = RendaStrategy.builder().build();
        faker = new Faker();
    }

    @Test
    public void deveObterPontuacaoMaxima() {
//        int pontuacaoCalculada = rendaStrategy.obterPontuacao(
//                Familia.builder()
//                        .rendaTotal(RendaStrategy.RENDA_MINIMA)
//                        .build()
//        );
//        Assert.assertEquals(RendaStrategy.PONTUACAO_MAXIMA, pontuacaoCalculada);
    }

    @Test
    public void deveObterPontuacaoMediaTetoRendaMaxima() {
//        int pontuacaoCalculada = rendaStrategy.obterPontuacao(
//                Familia.builder()
//                        .rendaTotal(RendaStrategy.RENDA_MAXIMA)
//                        .build()
//        );
//        Assert.assertEquals(RendaStrategy.PONTUACAO_MEDIA, pontuacaoCalculada);
//
    }

    @Test
    public void deveObterPontuacaoMedia() {
//        int pontuacaoCalculada = rendaStrategy.obterPontuacao(
//                Familia.builder()
//                        .rendaTotal(BigDecimal.valueOf(faker.number().numberBetween(900, 1500)))
//                        .build()
//        );
//        Assert.assertEquals(RendaStrategy.PONTUACAO_MEDIA, pontuacaoCalculada);
//
    }
    @Test
    public void deveObterPontuacaoMinima() {
//        int pontuacaoCalculada = rendaStrategy.obterPontuacao(
//                Familia.builder()
//                        .rendaTotal(BigDecimal.valueOf(faker.number().numberBetween(1501, 6000)))
//                        .build()
//        );
//        Assert.assertEquals(RendaStrategy.PONTUACAO_MINIMA, pontuacaoCalculada);
//
    }
}
