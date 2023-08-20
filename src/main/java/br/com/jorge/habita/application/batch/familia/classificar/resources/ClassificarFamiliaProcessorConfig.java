package br.com.jorge.habita.application.batch.familia.classificar.resources;

import br.com.jorge.habita.domain.strategy.CriterioAvalicaoStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ClassificarFamiliaProcessorConfig {

    @Bean
    public ClassificarFamiliaProcessor classificarFamiliaProcessor(List<CriterioAvalicaoStrategy> criterioAvalicaoStrategies) {
        return new ClassificarFamiliaProcessor(criterioAvalicaoStrategies);
    }
}
