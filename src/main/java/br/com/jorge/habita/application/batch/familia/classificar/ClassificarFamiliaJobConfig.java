package br.com.jorge.habita.application.batch.familia.classificar;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClassificarFamiliaJobConfig {

    @Bean
    public Job job(
                    JobRepository jobRepository,
                    @Qualifier("classificarFamiliaStep") Step classificarFamiliaStep
    ) {
        return new JobBuilder("job", jobRepository)
                .start(classificarFamiliaStep)
                .build();
    }
}
