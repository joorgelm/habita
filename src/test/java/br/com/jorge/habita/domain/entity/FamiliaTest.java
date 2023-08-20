package br.com.jorge.habita.domain.entity;

import br.com.jorge.habita.application.service.familia.io.FamiliaOutput;
import br.com.jorge.habita.domain.strategy.CriterioAvalicaoStrategy;
import net.datafaker.Faker;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class FamiliaTest {

    private static final Faker faker = new Faker();

    @Autowired
    private TestEntityManager entityManager;

    private List<CriterioAvalicaoStrategy> mockCriterioAvaliacaoStrategies;

    @Test
    public void deveCriarObjetoFamilia() {
        BigDecimal rendaTotal = BigDecimal.valueOf(faker.number().positive());
        Familia familia = entityManager.persistAndFlush(new Familia(rendaTotal));

        assertNotNull(familia);
        assertEquals(rendaTotal, familia.getRendaTotal());
        assertEquals(1L, (long) familia.getId());
        assertNotNull(familia.getDataCadastro());
        assertNull(familia.getDistribuicao());
        assertNull(familia.getPontuacao());
    }

    @Test
    public void deveReceberNovaPontuacao() {
        int pontuacao = faker.number().positive();
        BigDecimal rendaTotal = BigDecimal.valueOf(faker.number().positive());
        CriterioAvalicaoStrategy mockCriterio = mock(CriterioAvalicaoStrategy.class);
        Familia familia = entityManager.persistAndFlush(new Familia(rendaTotal));

        when(mockCriterio.obterPontuacao(familia)).thenReturn(pontuacao);
        mockCriterioAvaliacaoStrategies = List.of(mockCriterio);

        familia.atualizarPontuacao(mockCriterioAvaliacaoStrategies);
        verify(mockCriterioAvaliacaoStrategies.get(0), times(1)).obterPontuacao(familia);
        assertEquals(Integer.valueOf(pontuacao), familia.getPontuacao());
    }

    @Test(expected = IllegalStateException.class)
    public void deveFalharAoReceberPontuacaoNegativa() {
        int pontuacao = faker.number().negative();
        BigDecimal rendaTotal = BigDecimal.valueOf(faker.number().positive());
        CriterioAvalicaoStrategy mockCriterio = mock(CriterioAvalicaoStrategy.class);
        Familia familia = new Familia(rendaTotal);

        when(mockCriterio.obterPontuacao(familia)).thenReturn(pontuacao);
        mockCriterioAvaliacaoStrategies = List.of(mockCriterio);
        familia.atualizarPontuacao(mockCriterioAvaliacaoStrategies);
    }

    @Test
    public void deveGerarOutput() {
        BigDecimal rendaTotal = BigDecimal.valueOf(faker.number().positive());

        Familia familia = entityManager.persistAndFlush(new Familia(rendaTotal));
        familia.adicionaMembro(criarMembro(35, familia));

        FamiliaOutput output = familia.toOutput();

        assertNotNull(output);
        assertEquals(1L, (long) output.id());
        assertEquals(familia.getMembros().size(), output.membros().size());
    }

    @Test
    public void deveContarDoisDepententes() {
        Familia familia = new Familia();

        familia.adicionaMembro(criarMembro(35, familia));
        familia.adicionaMembro(criarMembro(15, familia));
        familia.adicionaMembro(criarMembro(17, familia));

        Assert.assertEquals(2, familia.quantiadeDependentes());
    }

    @Test(expected = IllegalStateException.class)
    public void deveFalharAoCadastrarFamiliaSemRenda() {
        new Familia(null);
    }

    @Test(expected = IllegalStateException.class)
    public void deveFalharAoCadastrarFamiliaComRendaNegativa() {
        BigDecimal rendaTotal = BigDecimal.valueOf(faker.number().negative());
        new Familia(rendaTotal);
    }

    @Test
    public void deveContarZeroDepententes() {
        Familia familia = new Familia();

        familia.adicionaMembro(criarMembro(35, familia));
        familia.adicionaMembro(criarMembro(18, familia));
        familia.adicionaMembro(criarMembro(18, familia));

        Assert.assertEquals(0, familia.quantiadeDependentes());
    }

    private Membro criarMembro(int idade, Familia familia) {
        return new Membro(faker.name().fullName(), idade, familia);
    }
}
