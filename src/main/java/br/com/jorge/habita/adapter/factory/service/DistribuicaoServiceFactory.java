package br.com.jorge.habita.adapter.factory.service;

import br.com.jorge.habita.domain.service.DistribuicaoService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DistribuicaoServiceFactory {

    @Bean
    public DistribuicaoService createDistribuicaoService() {
        return DistribuicaoService.builder().build();
    }
}
