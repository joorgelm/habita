package br.com.jorge.habita.application.batch.familia.classificar.resources;

import br.com.jorge.habita.domain.entity.Familia;
import org.springframework.batch.integration.async.AsyncItemWriter;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Future;

@Configuration
public class FamiliaAsyncWriterConfig {

    @Bean
    public ItemWriter<Future<Familia>> familiaAsyncWriter(FamiliaWriter familiaWriter) {
        AsyncItemWriter<Familia> familiaAsyncItemWriter = new AsyncItemWriter<>();
        familiaAsyncItemWriter.setDelegate(familiaWriter);
        return familiaAsyncItemWriter;
    }
}
