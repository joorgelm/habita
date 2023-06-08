package br.com.jorge.habita.application.batch.familia.classificar;

import br.com.jorge.habita.application.batch.familia.classificar.resources.ClassificarFamiliaProcessor;
import br.com.jorge.habita.application.batch.familia.classificar.resources.FamiliaReader;
import br.com.jorge.habita.domain.entity.Familia;
import br.com.jorge.habita.domain.service.AnaliseFamiliarService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
class ClassificarFamiliaProcessorTest {

    @Autowired
    private FamiliaReader familiaReader;

    @Autowired
    private AnaliseFamiliarService analiseFamiliarService;

    private ClassificarFamiliaProcessor classificarFamiliaProcessor;

    @BeforeEach
    void setup() {
        classificarFamiliaProcessor = ClassificarFamiliaProcessor
                .builder()
                .analiseFamiliarService(analiseFamiliarService)
                .build();
    }

    @Test
    void deveClassificarFamilias() throws Exception {
        familiaReader.open(new ExecutionContext());
        Familia familia;

        while ((familia = familiaReader.read()) != null) {
            Familia familiaClassificada = classificarFamiliaProcessor.process(familia);

            Assertions.assertNotNull(familiaClassificada);
            Assertions.assertNotNull(familiaClassificada.getPontuacao());
        }
    }
}
