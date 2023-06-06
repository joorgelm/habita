package br.com.jorge.habita.application.usecase.familia;

import br.com.jorge.habita.application.repository.FamiliaRepository;
import br.com.jorge.habita.application.repository.MembroRepository;
import br.com.jorge.habita.application.usecase.familia.cadastrar.CadastrarFamiliaInput;
import br.com.jorge.habita.application.usecase.familia.cadastrar.CadastrarFamiliaUsecase;
import br.com.jorge.habita.domain.entity.Familia;
import br.com.jorge.habita.domain.entity.Membro;
import br.com.jorge.habita.domain.exception.RendaInvalidaException;
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
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
public class CadastrarFamiliaUsecaseTest {

    @Mock
    private MembroRepository membroRepository;
    @Mock
    private FamiliaRepository familiaRepository;

    @Captor
    private ArgumentCaptor<Familia> familiaArgumentCaptor;
    @Captor
    private ArgumentCaptor<List<Membro>> membroArgumentCaptor;

    private static final Faker faker = new Faker();

    private CadastrarFamiliaUsecase usecase;

    @Before
    public void setup() {
        usecase = CadastrarFamiliaUsecase
                .builder()
                .familiaRepository(familiaRepository)
                .membroRepository(membroRepository)
                .build();
    }

    @Test
    public void deveCadastrarFamiliaComRendaEMembros() {
        CadastrarFamiliaInput input = CadastrarFamiliaInput
                .builder()
                .membros(mockListaMembros(4))
                .rendaTotal(BigDecimal.valueOf(faker.number().numberBetween(300, 2000)))
                .build();

        Mockito.doReturn(Familia.builder().build()).when(familiaRepository).save(familiaArgumentCaptor.capture());

        usecase.cadastrarFamilia(input);
        Mockito.verify(membroRepository, Mockito.times(1)).saveAll(membroArgumentCaptor.capture());

        Familia familiaSalva = familiaArgumentCaptor.getValue();
        List<Membro> membrosSalvos = membroArgumentCaptor.getValue();

        Assert.assertEquals(familiaSalva.getRendaTotal(), input.getRendaTotal());
        Assert.assertNull(familiaSalva.getPontuacao());
        Assert.assertNull(familiaSalva.getDistribuicao());
        Assert.assertEquals(membrosSalvos.size(), input.getMembros().size());
    }

    @Test(expected = RendaInvalidaException.class)
    public void deveFalharAoCadastrarFamiliaRendaInvalida() {
        CadastrarFamiliaInput input = CadastrarFamiliaInput
                .builder()
                .membros(mockListaMembros(4))
                .rendaTotal(BigDecimal.ZERO)
                .build();

        usecase.cadastrarFamilia(input);
    }

    private List<CadastrarFamiliaInput.Membro> mockListaMembros(int quantidadeMembros) {
        return IntStream.rangeClosed(1, quantidadeMembros)
                .boxed()
                .map(integer -> mockMembro())
                .collect(Collectors.toList());
    }

    private CadastrarFamiliaInput.Membro mockMembro() {
        return CadastrarFamiliaInput.Membro
                .builder()
                .nome(faker.name().fullName())
                .idade(faker.number().numberBetween(1, 99))
                .build();
    }
}
