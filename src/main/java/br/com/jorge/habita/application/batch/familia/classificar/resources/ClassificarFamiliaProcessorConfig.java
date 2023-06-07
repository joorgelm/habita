package br.com.jorge.habita.application.batch.familia.classificar.resources;

import br.com.jorge.habita.domain.service.AnaliseFamiliarService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClassificarFamiliaProcessorConfig {

    @Bean
    public ClassificarFamiliaProcessor classificarFamiliaProcessor(AnaliseFamiliarService analiseFamiliarService) {
        return ClassificarFamiliaProcessor
                .builder()
                .analiseFamiliarService(analiseFamiliarService)
                .build();
    }
}
