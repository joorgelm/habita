package br.com.jorge.habita.domain.entity;

import br.com.jorge.habita.domain.strategy.CriterioAvalicaoStrategy;
import br.com.jorge.habita.domain.strategy.DependentesStrategy;
import br.com.jorge.habita.domain.strategy.RendaStrategy;
import net.datafaker.Faker;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

public class FamiliaTest {

    private Faker faker;

    private Familia familia;
    private List<CriterioAvalicaoStrategy> criterioAvaliacaoStrategies;

    @Before
    public void setup() {
        criterioAvaliacaoStrategies = List.of(
                RendaStrategy.builder().build(),
                DependentesStrategy.builder().build()
        );

        faker = new Faker();
    }

    @Test
    public void familiaDeveReceberPontuacaoMaxima() {
        Integer pontuacaoMaxima = criterioAvaliacaoStrategies.stream()
                .map(CriterioAvalicaoStrategy::pontuacaoMaxima)
                .reduce(0, Integer::sum);

        List<Membro> membros = List.of(
                criarMembro(20),
                criarMembro(10),
                criarMembro(12),
                criarMembro(15)
        );
        familia = Familia.builder()
                .pontuacao(null)
                .rendaTotal(RendaStrategy.RENDA_MINIMA)
                .membros(membros)
                .build();

        familia.atualizarPontuacao(criterioAvaliacaoStrategies);
        Assert.assertEquals(pontuacaoMaxima, familia.getPontuacao());
    }

    @Test
    public void familiaDeveReceberPontuacaoMinima() {
        Integer pontuacaoMinima = criterioAvaliacaoStrategies.stream()
                .map(CriterioAvalicaoStrategy::pontuacaoMinima)
                .reduce(0, Integer::sum);

        List<Membro> membros = List.of(
                criarMembro(20),
                criarMembro(30),
                criarMembro(18)
        );
        familia = Familia.builder()
                .pontuacao(null)
                .rendaTotal(RendaStrategy.RENDA_MAXIMA.add(BigDecimal.ONE))
                .membros(membros)
                .build();

        familia.atualizarPontuacao(criterioAvaliacaoStrategies);
        Assert.assertEquals(pontuacaoMinima, familia.getPontuacao());
    }

    @Test
    public void deveContarDoisDepententes() {
        List<Membro> membros = List.of(
                criarMembro(35),
                criarMembro(15),
                criarMembro(17)
        );

        familia = Familia.builder()
                .pontuacao(null)
                .membros(membros)
                .build();

        Assert.assertEquals(2, familia.quantiadeDependentes());
    }

    @Test
    public void deveContarZeroDepententes() {
        List<Membro> membros = List.of(
                criarMembro(35),
                criarMembro(18),
                criarMembro(18)
        );

        familia = Familia.builder()
                .pontuacao(null)
                .membros(membros)
                .build();

        Assert.assertEquals(0, familia.quantiadeDependentes());
    }

    private Membro criarMembro(int idade) {
        return Membro.builder()
                .nome(faker.name().fullName())
                .idade(idade)
                .build();
    }
}
