package br.com.jorge.habita.application.batch.familia.classificar.step;

import br.com.jorge.habita.application.batch.familia.classificar.resources.ClassificarFamiliaProcessor;
import br.com.jorge.habita.application.batch.familia.classificar.resources.FamiliaReader;
import br.com.jorge.habita.application.batch.familia.classificar.resources.FamiliaWriter;
import br.com.jorge.habita.domain.entity.Familia;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class ClassificarFamiliaStepConfig {

    @Bean
    public Step classificarFamiliaStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            FamiliaReader familiaItemReader,
            ClassificarFamiliaProcessor classificarFamiliaProcessor,
            FamiliaWriter familiaWriter
    ) {
        return new StepBuilder("classificarFamiliaStep", jobRepository)
                .<Familia, Familia>chunk(1, transactionManager)
                .reader(familiaItemReader)
                .processor(classificarFamiliaProcessor)
                .writer(familiaWriter)
                .build();
    }
}
