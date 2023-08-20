package br.com.jorge.habita.domain.entity;


import br.com.jorge.habita.application.service.distribuicao.io.DistribuicaoOutput;
import br.com.jorge.habita.application.service.familia.io.FamiliaOutput;
import net.datafaker.Faker;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class DistribuicaoTest {

    private static final Faker faker = new Faker();

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void deveGerarDistribuicao() {
        Distribuicao distribuicao = new Distribuicao();
        Distribuicao persisted = entityManager.persistAndFlush(distribuicao);

        assertNotNull(distribuicao);
        assertNotNull(distribuicao.getDistribuicaoData());
        assertEquals(1L, (long) persisted.getId());
        assertNotNull(distribuicao.getDistribuicaoData());
    }

    @Test
    public void deveGerarOutput() {

        FamiliaOutput familiaOutput = new FamiliaOutput((long) faker.number().positive(), List.of());
        Distribuicao distribuicao = entityManager.persistAndFlush(new Distribuicao());
        DistribuicaoOutput output = distribuicao
                .toOutput(List.of(familiaOutput));

        assertNotNull(output);
        assertEquals(1L, (long) output.id());
        assertNotNull(output.dataDistribuicao());
        assertFalse(output.familiasContempladas().isEmpty());
    }
}