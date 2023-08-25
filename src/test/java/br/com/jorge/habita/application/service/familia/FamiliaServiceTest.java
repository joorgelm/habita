package br.com.jorge.habita.application.service.familia;

import br.com.jorge.habita.application.batch.familia.classificar.dispatcher.ClassificarFamiliaDispatcher;
import br.com.jorge.habita.application.repository.FamiliaRepository;
import br.com.jorge.habita.application.service.familia.io.FamiliaInput;
import br.com.jorge.habita.application.service.membro.MembroService;
import br.com.jorge.habita.domain.entity.Familia;
import net.datafaker.Faker;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FamiliaServiceTest {

    @Mock
    private FamiliaRepository familiaRepository;

    @Mock
    private MembroService membroService;

    @Mock
    private ClassificarFamiliaDispatcher classificarFamiliaDispatcher;

    @Captor
    private ArgumentCaptor<Familia> familiaArgumentCaptor;

    private FamiliaService familiaService;

    private static final Faker faker = new Faker();

    @Before
    public void setUp() {
        this.familiaService = new FamiliaServiceImpl(
                familiaRepository,
                membroService,
                classificarFamiliaDispatcher
        );
    }

    @Test
    public void deveCadastrarFamilia() {
        List<FamiliaInput.Membro> membroList = List.of(mock(FamiliaInput.Membro.class));
        Familia mockFamiliaRetornadaPelaRepository = mock(Familia.class);

        FamiliaInput input = new FamiliaInput(
                BigDecimal.valueOf(faker.number().numberBetween(300, 2000)),
                membroList
        );


        when(familiaRepository.save(familiaArgumentCaptor.capture()))
                .thenReturn(mockFamiliaRetornadaPelaRepository);

        familiaService.cadastrarFamilia(input);

        Familia familiaPassadaParaRepository = familiaArgumentCaptor.getValue();

        verify(familiaRepository, times(1)).save(familiaPassadaParaRepository);
        verify(membroService, times(1)).cadastrarListaDeMembros(membroList, mockFamiliaRetornadaPelaRepository);

        assertNotNull(familiaPassadaParaRepository);
        assertEquals(input.getRendaTotal(), familiaPassadaParaRepository.getRendaTotal());
    }

    @Test(expected = IllegalStateException.class)
    public void deveFalharAoCadastrarFamiliaSemRenda() {
        FamiliaInput input = new FamiliaInput(null, null);

        familiaService.cadastrarFamilia(input);
    }

    @Test(expected = IllegalStateException.class)
    public void deveFalharAoCadastrarFamiliaComRendaNegativa() {
        FamiliaInput input = new FamiliaInput(BigDecimal.valueOf(-10L), null);

        familiaService.cadastrarFamilia(input);
    }
}