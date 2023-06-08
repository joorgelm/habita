package br.com.jorge.habita.application.batch.familia.classificar;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@SpringBatchTest
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureTestDatabase
class ClassificarFamiliaJobTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Test
    void deveExecutarJobClassificarFamiliaEFinalizarComSucesso(
            @Qualifier("classificarFamilia") Job classificarFamilia
    ) throws Exception {
        jobLauncherTestUtils.setJob(classificarFamilia);
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();
        assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());

        jobExecution.getStepExecutions().forEach(stepExecution -> {
            assertEquals(BatchStatus.COMPLETED, stepExecution.getStatus());
        });
    }
}
