package br.com.jorge.habita.application.batch.familia.classificar.step;

import br.com.jorge.habita.application.repository.FamiliaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@SpringBatchTest
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureTestDatabase
class ClassificarFamiliaStepTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private FamiliaRepository familiaRepository;

    @Test
    void deveClassificarTodasFamiliasDoBancoEmMemoria() {
        JobExecution jobExecution = jobLauncherTestUtils.launchStep("classificarFamiliaStep");

        assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());

        long count = familiaRepository.count();

        StepExecution stepExecution = jobExecution.getStepExecutions().iterator().next();
        assertEquals(count, stepExecution.getReadCount());
        assertEquals(count, stepExecution.getWriteCount());
    }
}
