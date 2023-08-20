package br.com.jorge.habita.application.service.distribuicao;

import br.com.jorge.habita.application.repository.DistribuicaoRepository;
import br.com.jorge.habita.application.service.familia.FamiliaService;
import br.com.jorge.habita.domain.entity.Distribuicao;
import br.com.jorge.habita.domain.entity.Familia;
import net.datafaker.Faker;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;

import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DistribuicaoServiceTest {

    private DistribuicaoService distribuicaoService;

    @Mock
    private FamiliaService familiaService;

    @Mock
    private DistribuicaoRepository distribuicaoRepository;

    @Captor
    private ArgumentCaptor<Distribuicao> distribuicaoArgumentCaptor;

    private static final Faker faker = new Faker();

    @Before
    public void setUp() {
        this.distribuicaoService = new DistribuicaoServiceImpl(familiaService, distribuicaoRepository);
    }

    @Test
    public void deveRealizarDistribuicaoCasas() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        int qtdCasas = faker.number().positive();

        Distribuicao distribuicao = mock(Distribuicao.class);
        Familia familia = mock(Familia.class);

        when(familiaService.buscarFamiliasContempladas(qtdCasas))
                .thenReturn(List.of(familia));

        when(distribuicaoRepository.save(distribuicaoArgumentCaptor.capture()))
                .thenReturn(distribuicao);

        distribuicaoService.realizarDistribuicaoDeCasas(qtdCasas);


        verify(familiaService, times(1))
                .buscarFamiliasContempladas(qtdCasas);

        verify(familiaService, times(1))
                .homologarFamiliaContemplada(familia, distribuicao);
    }
}