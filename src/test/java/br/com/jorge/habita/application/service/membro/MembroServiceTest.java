package br.com.jorge.habita.application.service.membro;

import br.com.jorge.habita.application.repository.MembroRepository;
import br.com.jorge.habita.application.service.familia.io.FamiliaInput;
import br.com.jorge.habita.domain.entity.Familia;
import br.com.jorge.habita.domain.entity.Membro;
import net.datafaker.Faker;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MembroServiceTest {

    @Mock
    private MembroRepository membroRepository;

    @Captor
    private ArgumentCaptor<List<Membro>> membroListArgumentCaptor;

    private MembroService membroService;

    private static final Faker faker = new Faker();

    @Before
    public void setUp() {
        membroService = new MembroServiceImpl(membroRepository);
    }

    @Test
    public void deveCadastrarListaDeMembros() {
        FamiliaInput.Membro membroInput = new FamiliaInput.Membro(
                faker.name().fullName(), faker.number().numberBetween(1, 99)
        );

        Familia familiaMock = mock(Familia.class);
        List<FamiliaInput.Membro> membroInputList = List.of(membroInput);

        membroService.cadastrarListaDeMembros(membroInputList, familiaMock);

        verify(membroRepository,times(1)).saveAll(membroListArgumentCaptor.capture());
        List<Membro> membrosRecebidosNaRepository = membroListArgumentCaptor.getValue();

        assertEquals(membroInputList.size(), membrosRecebidosNaRepository.size());
        assertEquals(membroInput.getNome(), membrosRecebidosNaRepository.get(0).getNome());
        assertEquals(membroInput.getIdade(), membrosRecebidosNaRepository.get(0).getIdade());
        assertEquals(familiaMock, membrosRecebidosNaRepository.get(0).getFamilia());
    }
}
