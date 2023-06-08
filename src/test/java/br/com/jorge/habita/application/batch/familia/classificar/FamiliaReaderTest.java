package br.com.jorge.habita.application.batch.familia.classificar;

import br.com.jorge.habita.application.batch.familia.classificar.resources.FamiliaReader;
import br.com.jorge.habita.domain.entity.Familia;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@SpringBatchTest
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureTestDatabase
class FamiliaReaderTest {

    @Autowired
    private FamiliaReader familiaReader;

    @Test
    void deveLerFamilias() throws Exception {
        familiaReader.open(new ExecutionContext());
        Familia familia;

        while ((familia = familiaReader.read()) != null) {
            Assertions.assertNotNull(familia);
        }
    }
}
