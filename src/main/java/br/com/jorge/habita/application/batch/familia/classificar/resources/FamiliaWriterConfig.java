package br.com.jorge.habita.application.batch.familia.classificar.resources;

import br.com.jorge.habita.application.repository.FamiliaRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FamiliaWriterConfig {

    @Bean
    public FamiliaWriter familiaWriter(FamiliaRepository familiaRepository) {
        FamiliaWriter familiaWriter = FamiliaWriter
                .builder()
                .build();

        familiaWriter.setRepository(familiaRepository);

        return familiaWriter;
    }
}
