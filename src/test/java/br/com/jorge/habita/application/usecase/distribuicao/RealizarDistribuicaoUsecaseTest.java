package br.com.jorge.habita.application.usecase.distribuicao;

import br.com.jorge.habita.application.batch.familia.classificar.launcher.ClassificarFamiliaJobLauncher;
import br.com.jorge.habita.application.repository.DistribuicaoRepository;
import br.com.jorge.habita.application.repository.FamiliaRepository;
import br.com.jorge.habita.application.usecase.distribuicao.converter.RealizarDistribuicaoConverter;
import br.com.jorge.habita.domain.entity.Familia;
import br.com.jorge.habita.domain.entity.Membro;
import br.com.jorge.habita.domain.exception.DistribuicaoIncompletaException;
import br.com.jorge.habita.domain.service.AnaliseFamiliarService;
import br.com.jorge.habita.domain.service.DistribuicaoService;
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
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;

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

    @Mock
    private ClassificarFamiliaJobLauncher jobLauncher;

    @Mock
    private Job classificarFamilia;


    @Captor
    private ArgumentCaptor<List<Familia>> listFamiliaCaptor;
    private static final Faker faker = new Faker();

    private RealizarDistribuicaoUsecase usecase;
    @Before
    public void setup() {
        usecase = RealizarDistribuicaoUsecase
                .builder()
                .analiseFamiliarService(
                        AnaliseFamiliarService.builder().criterioAvalicaoStrategies(buscarCriterios()).build()
                )
                .distribuicaoService(DistribuicaoService.builder().build())
                .distribuicaoRepository(distribuicaoRepository)
                .familiaRepository(familiaRepository)
                .classificarFamilia(classificarFamilia)
                .jobLauncher(jobLauncher)
                .build();
    }

    private List<CriterioAvalicaoStrategy> buscarCriterios() {
        return List.of(
                DependentesStrategy.builder().build(),
                RendaStrategy.builder().build()
                );
    }

    @Test
    public void deveRealizarDistribuicao() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        int qtdCasas = 3;
        int qtdTotalFamilias = 5;
        List<Familia> familiasContempladasMock = mockFamiliasContempladas(qtdTotalFamilias);

        Mockito.when(familiaRepository.findByDistribuicaoIsNullOrderByPontuacaoDesc(qtdCasas))
                .thenReturn(familiasContempladasMock);

        RealizarDistribuicaoOutput distribuicao = usecase.realizarDistribuicao(
                RealizarDistribuicaoInput
                        .builder()
                        .qtdCasas(qtdCasas)
                        .build()
        );

        Mockito.verify(familiaRepository, Mockito.times(1)).saveAll(listFamiliaCaptor.capture());
        Mockito.verify(jobLauncher, Mockito.times(1)).run(Mockito.eq(classificarFamilia), Mockito.any());
        Assert.assertEquals(distribuicao.getFamiliasContempladas().size(), familiasContempladasMock.size());
        Assert.assertEquals(RealizarDistribuicaoConverter.converter(familiasContempladasMock), distribuicao);
    }

    @Test(expected = DistribuicaoIncompletaException.class)
    public void deveFalharAoTentarRealizarDistribuicaoSemFamiliasElegiveis() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        int qtdCasas = faker.number().numberBetween(2, 20);
        Mockito.when(familiaRepository.findByDistribuicaoIsNullOrderByPontuacaoDesc(qtdCasas))
                .thenReturn(mockFamiliasContempladas(qtdCasas - 1));

        usecase.realizarDistribuicao(
                RealizarDistribuicaoInput
                        .builder()
                        .qtdCasas(qtdCasas)
                        .build()
        );
    }

    private List<Familia> mockFamiliasContempladas(int qtdCasas) {

        return IntStream.rangeClosed(1, qtdCasas)
                .boxed()
                .map(integer -> mockFamilia(faker.number().numberBetween(3, 6)))
                .sorted(Comparator.comparing(Familia::getPontuacao).reversed())
                .limit(qtdCasas)
                .toList();
    }

    private Familia mockFamilia(int qtdMembros) {
        return null;
//        return Familia
//                .builder()
//                .rendaTotal(BigDecimal.valueOf(faker.number().numberBetween(100, 1500)))
//                .pontuacao(faker.number().numberBetween(0, 8))
//                .membros(mockListMembros(qtdMembros))
//                .distribuicao(null)
//                .build();
    }

    private List<Membro> mockListMembros(int qtdMembros) {
        return IntStream.rangeClosed(1, qtdMembros)
                .boxed()
                .map(integer -> mockMembro())
                .toList();
    }

    private static Membro mockMembro() {
        return null;
//        return Membro
//                .builder()
//                .nome(faker.name().fullName())
//                .idade(faker.number().numberBetween(1, 100))
//                .build();
    }
}
