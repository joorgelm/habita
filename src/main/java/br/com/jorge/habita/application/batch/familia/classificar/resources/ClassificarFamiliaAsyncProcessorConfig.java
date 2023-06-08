package br.com.jorge.habita.application.batch.familia.classificar.resources;

import br.com.jorge.habita.domain.entity.Familia;
import org.springframework.batch.integration.async.AsyncItemProcessor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;

import java.util.concurrent.Future;

@Configuration
public class ClassificarFamiliaAsyncProcessorConfig {

    @Bean
    public ItemProcessor<Familia, Future<Familia>> classificarFamiliaAsyncProcessor(
            ClassificarFamiliaProcessor classificarFamiliaProcessor,
            TaskExecutor taskExecutor
    ) {
        AsyncItemProcessor<Familia, Familia> familiaAsyncItemProcessor = new AsyncItemProcessor<>();
        familiaAsyncItemProcessor.setTaskExecutor(taskExecutor);
        familiaAsyncItemProcessor.setDelegate(classificarFamiliaProcessor);
        return familiaAsyncItemProcessor;
    }
}
