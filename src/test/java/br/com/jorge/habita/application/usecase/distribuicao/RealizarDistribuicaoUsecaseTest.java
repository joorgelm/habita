package br.com.jorge.habita.application.usecase.distribuicao;

import br.com.jorge.habita.adapter.repository.DistribuicaoRepository;
import br.com.jorge.habita.adapter.repository.FamiliaRepository;
import br.com.jorge.habita.application.usecase.distribuicao.converter.RealizarDistribuicaoConverter;
import br.com.jorge.habita.domain.entity.Distribuicao;
import br.com.jorge.habita.domain.entity.Familia;
import br.com.jorge.habita.domain.entity.Membro;
import br.com.jorge.habita.domain.exception.DistribuicaoIncompletaException;
import br.com.jorge.habita.domain.strategy.CriterioAvalicaoStrategy;
import br.com.jorge.habita.domain.strategy.DependentesStrategy;
import br.com.jorge.habita.domain.strategy.RendaStrategy;
import net.datafaker.Faker;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
public class RealizarDistribuicaoUsecaseTest {

    @Mock
    private DistribuicaoRepository distribuicaoRepository;
    @Mock
    private FamiliaRepository familiaRepository;

    @Captor
    private ArgumentCaptor<List<Familia>> listFamiliaCaptor;

    @Captor
    private ArgumentCaptor<Distribuicao> distribuicaoCaptor;

    private List<CriterioAvalicaoStrategy> criterioAvalicaoStrategies;

    private static final Faker faker = new Faker();

    private RealizarDistribuicaoUsecase usecase;
    @Before
    public void setup() {
        usecase = RealizarDistribuicaoUsecase
                .builder()
                .criterioAvalicaoStrategies(buscarCriterios())
                .distribuicaoRepository(distribuicaoRepository)
                .familiaRepository(familiaRepository)
                .build();
    }

    private List<CriterioAvalicaoStrategy> buscarCriterios() {
        return List.of(
                DependentesStrategy.builder().build(),
                RendaStrategy.builder().build()
                );
    }

    @Test
    public void deveRealizarDistribuicao() {
        int qtdCasas = 3;
        int qtdTotalFamilias = 5;
        List<Familia> familiasMock = mockFamiliaList(qtdTotalFamilias);

        Mockito.when(familiaRepository.findAll())
                .thenReturn(familiasMock);

        List<Familia> familiasContempladasMock = familiasMock
                .stream()
                .sorted(Comparator.comparing(Familia::getPontuacao).reversed())
                .limit(qtdCasas)
                .toList();
        Mockito.when(familiaRepository.findByDistribuicaoIsNullOrderByPontuacaoDesc(qtdCasas))
                .thenReturn(familiasContempladasMock);

        RealizarDistribuicaoOutput distribuicao = usecase.realizarDistribuicao(qtdCasas);

        Mockito.verify(familiaRepository, Mockito.times(2)).saveAll(listFamiliaCaptor.capture());
        Assert.assertEquals(distribuicao.getFamiliasContempladas().size(), familiasContempladasMock.size());
        Assert.assertEquals(RealizarDistribuicaoConverter.converter(familiasContempladasMock), distribuicao);
    }

    @Test(expected = DistribuicaoIncompletaException.class)
    public void deveFalharAoTentarRealizarDistribuicaoSemFamiliasElegiveis() {
        int qtdCasas = faker.number().numberBetween(2, 20);
        Mockito.when(familiaRepository.findByDistribuicaoIsNullOrderByPontuacaoDesc(qtdCasas))
                .thenReturn(mockFamiliaList(qtdCasas - 1));

        usecase.realizarDistribuicao(qtdCasas);
    }

    private List<Familia> mockFamiliaList(int qtdFamilia) {
        return IntStream.rangeClosed(1, qtdFamilia)
                .boxed()
                .map(integer -> mockFamilia(faker.number().numberBetween(3, 6)))
                .toList();
    }

    private Familia mockFamilia(int qtdMembros) {
        return Familia
                .builder()
                .rendaTotal(BigDecimal.valueOf(faker.number().numberBetween(100, 1500)))
                .pontuacao(faker.number().numberBetween(0, 8))
                .membros(mockListMembros(qtdMembros))
                .distribuicao(null)
                .build();
    }

    private List<Membro> mockListMembros(int qtdMembros) {
        return IntStream.rangeClosed(1, qtdMembros)
                .boxed()
                .map(integer -> mockMembro())
                .toList();
    }

    private static Membro mockMembro() {
        return Membro
                .builder()
                .nome(faker.name().fullName())
                .idade(faker.number().numberBetween(1, 100))
                .build();
    }
}
