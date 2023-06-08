package br.com.jorge.habita.domain.entity;

import net.datafaker.Faker;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class FamiliaTest {

    private Faker faker;

    private Familia familia;

    @Before
    public void setup() {
        faker = new Faker();
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
