package br.com.jorge.habita.adapter.factory.service;

import br.com.jorge.habita.domain.service.AnaliseFamiliarService;
import br.com.jorge.habita.domain.strategy.CriterioAvalicaoStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class AnaliseFamiliarServiceFactory {

    @Bean
    public AnaliseFamiliarService createPontuacaoService(List<CriterioAvalicaoStrategy> criterioAvalicaoStrategies) {
        return AnaliseFamiliarService.builder()
                .criterioAvalicaoStrategies(criterioAvalicaoStrategies)
                .build();
    }
}
