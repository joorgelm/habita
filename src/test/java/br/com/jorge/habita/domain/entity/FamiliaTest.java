package br.com.jorge.habita.domain.entity;

import net.datafaker.Faker;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

public class FamiliaTest {

    private Faker faker;

    @Before
    public void setup() {
        faker = new Faker();
    }

    @Test
    public void deveCriarFamiliaSemMembros() {
        Familia familia = new Familia(id(), renda());

        assertNotNull(familia);
        assertEquals(0, familia.getMembros().size());
        assertNull(familia.getDistribuicao());
        assertEquals(0L, familia.quantiadeDependentes());
    }

    @Test(expected = IllegalStateException.class)
    public void deveFalharAoCriarFamiliaSemRenda() {
        new Familia(id(), null);
    }

    @Test(expected = IllegalStateException.class)
    public void deveFalharAoCriarFamiliaComRendaNegativa() {
        BigDecimal rendaTotal = BigDecimal.valueOf(faker.number().negative());
        new Familia(id(), rendaTotal);
    }

    @Test
    public void deveRetornarQuantidadeDeDependentes() {
        Familia familia = new Familia(id(), renda());

        familia.adicionaMembro(criarMembroComIdade(35));
        familia.adicionaMembro(criarMembroComIdade(15));
        familia.adicionaMembro(criarMembroComIdade(17));

        assertEquals(2, familia.quantiadeDependentes());
    }


    //    @Test
//    public void deveContarDoisDepententes() {
//        List<Membro> membros = List.of(
//                criarMembro(35),
//                criarMembro(15),
//                criarMembro(17)
//        );
//
//        familia = Familia.builder()
//                .pontuacao(null)
//                .membros(membros)
//                .build();
//
//        Assert.assertEquals(2, familia.quantiadeDependentes());
//    }

//    @Test
//    public void deveContarZeroDepententes() {
//        List<Membro> membros = List.of(
//                criarMembro(35),
//                criarMembro(18),
//                criarMembro(18)
//        );
//
//        familia = Familia.builder()
//                .pontuacao(null)
//                .membros(membros)
//                .build();
//
//        Assert.assertEquals(0, familia.quantiadeDependentes());
//    }
//
    private BigDecimal renda() {
        return BigDecimal.valueOf(faker.number().positive());
    }

    private Long id() {
        return faker.number().numberBetween(1L, 1000L);
    }

    private Membro criarMembroComIdade(int idade) {
        return new Membro(faker.name().fullName(), idade);
    }
}
