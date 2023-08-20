package br.com.jorge.habita.domain.entity;

import br.com.jorge.habita.application.service.membro.io.MembroOutput;
import net.datafaker.Faker;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class MembroTest {

    private static final Faker faker = new Faker();

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void deveCriarMembro() {
        String nome = faker.name().fullName();
        Integer idade = faker.number().numberBetween(1, 100);
        Familia familia = criarFamilia();

        Membro membro = entityManager.persistAndFlush(new Membro(nome, idade, familia));
        assertNotNull(membro);
        assertEquals(1L, (long) membro.getId());
        assertEquals(nome, membro.getNome());
        assertEquals(idade, membro.getIdade());
        assertEquals(familia, membro.getFamilia());
    }

    private Familia criarFamilia() {
        return entityManager.persistAndFlush(
                new Familia(BigDecimal.valueOf(faker.number().positive()))
        );
    }

    @Test
    public void deveCriarOutput() {
        String nome = faker.name().fullName();
        Integer idade = faker.number().numberBetween(1, 100);
        Familia familia = criarFamilia();

        Membro membro = entityManager.persistAndFlush(new Membro(nome, idade, familia));
        MembroOutput membroOutput = membro.toOutput();
        assertNotNull(membroOutput);
        assertEquals(nome, membroOutput.nome());
    }

    @Test(expected = IllegalStateException.class)
    public void deveFalharAoCriarMembroComNomeNulo() {
        String nome = null;
        Integer idade = faker.number().numberBetween(1, 100);
        Familia familia = criarFamilia();

        new Membro(nome, idade, familia);
    }

    @Test(expected = IllegalStateException.class)
    public void deveFalharAoCriarMembroComNomeVazio() {
        String nome = "";
        Integer idade = faker.number().numberBetween(1, 100);
        Familia familia = criarFamilia();

        new Membro(nome, idade, familia);
    }

    @Test(expected = IllegalStateException.class)
    public void deveFalharAoCriarMembroSemIdade() {
        String nome = faker.name().fullName();
        Integer idade = null;
        Familia familia = criarFamilia();

        new Membro(nome, idade, familia);
    }

    @Test(expected = IllegalStateException.class)
    public void deveFalharAoCriarMembroComIdadeNegativa() {
        String nome = faker.name().fullName();
        Integer idade = faker.number().negative();
        Familia familia = criarFamilia();

        new Membro(nome, idade, familia);
    }

    @Test(expected = IllegalStateException.class)
    public void deveFalharAoCriarMembroSemFamilia() {
        String nome = faker.name().fullName();
        Integer idade = faker.number().negative();
        Familia familia = null;

        new Membro(nome, idade, familia);
    }
}