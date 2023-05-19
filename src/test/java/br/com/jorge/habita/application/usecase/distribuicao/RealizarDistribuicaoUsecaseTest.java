package br.com.jorge.habita.application.usecase.distribuicao;

import br.com.jorge.habita.adapter.repository.DistribuicaoRepository;
import br.com.jorge.habita.adapter.repository.FamiliaRepository;
import br.com.jorge.habita.domain.entity.Distribuicao;
import br.com.jorge.habita.domain.entity.Familia;
import br.com.jorge.habita.domain.entity.Membro;
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

    private static final Faker faker = new Faker();

    private RealizarDistribuicaoUsecase usecase;
    @Before
    public void setup() {
//        MockitoAnnotations.openMocks(this);
        usecase = RealizarDistribuicaoUsecase
                .builder()
                .distribuicaoRepository(distribuicaoRepository)
                .familiaRepository(familiaRepository)
                .build();
    }

    @Test
    public void deveRealizarDistribuicao() {
        int qtdCasas = 3;
        Mockito.when(familiaRepository.findByDistribuicaoIsNullOrderByPontuacaoDesc(qtdCasas))
                .thenReturn(mockFamiliaList(qtdCasas));

        RealizarDistribuicaoOutput sortear = usecase.sortear(qtdCasas);

        Mockito.verify(distribuicaoRepository, Mockito.times(1)).save(distribuicaoCaptor.capture());

        Mockito.verify(familiaRepository, Mockito.times(1)).saveAll(listFamiliaCaptor.capture());

        Assert.assertEquals(qtdCasas, listFamiliaCaptor.getValue().size());

        /*
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);

        // Chama o método a ser testado com um argumento específico
        myClassMock.doSomething("argumento de teste");

        // Verifica se o método foi chamado com o argumento correto
        verify(myClassMock).doSomething(argumentCaptor.capture());

        // Obtém o argumento capturado
        String argumentCapturado = argumentCaptor.getValue();
        * */

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

    @Test
    public void deveFalharQuandoNaoHouverSorteados() {
        RealizarDistribuicaoOutput sortear = usecase.sortear(5);
    }
}
