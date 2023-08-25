package br.com.jorge.habita.domain.strategy;

import br.com.jorge.habita.domain.entity.Familia;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DependentesStrategyTest {

    @Mock
    private Familia mockFamilia;

    private DependentesStrategy dependentesStrategy;

    @Before
    public void setup() {
        dependentesStrategy = new DependentesStrategy();
    }

    @Test
    public void deveObterPontuacaoMaxima() {
        Mockito.when(mockFamilia.quantiadeDependentes())
                .thenReturn(Long.parseLong(String.valueOf(DependentesStrategy.TETO_DEPENDENTES)));

        int pontuacaoCalculada = dependentesStrategy.obterPontuacao(mockFamilia);

        Assert.assertEquals(DependentesStrategy.PONTUACAO_MAXIMA, pontuacaoCalculada);
    }

    @Test
    public void deveObterPontuacaoMedia() {
        Mockito.when(mockFamilia.quantiadeDependentes())
                .thenReturn(Long.parseLong(String.valueOf(DependentesStrategy.PISO_DEPENDENTES)));

        int pontuacaoCalculada = dependentesStrategy.obterPontuacao(mockFamilia);

        Assert.assertEquals(DependentesStrategy.PONTUACAO_MEDIA, pontuacaoCalculada);
    }

    @Test
    public void deveObterPontuacaoMinima() {
        Mockito.when(mockFamilia.quantiadeDependentes())
                .thenReturn(Long.parseLong(String.valueOf(DependentesStrategy.PISO_DEPENDENTES - 1)));

        int pontuacaoCalculada = dependentesStrategy.obterPontuacao(mockFamilia);

        Assert.assertEquals(DependentesStrategy.PONTUACAO_MINIMA, pontuacaoCalculada);
    }

    @Test
    public void deveObterPontuacaoMinimaFamiliaSemDependentes() {
        Mockito.when(mockFamilia.quantiadeDependentes())
                .thenReturn(0L);

        int pontuacaoCalculada = dependentesStrategy.obterPontuacao(mockFamilia);
        Assert.assertEquals(DependentesStrategy.PONTUACAO_MINIMA, pontuacaoCalculada);
    }
}
