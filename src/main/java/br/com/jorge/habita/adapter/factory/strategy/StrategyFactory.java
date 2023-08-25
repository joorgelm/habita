package br.com.jorge.habita.adapter.factory.strategy;

import br.com.jorge.habita.domain.strategy.DependentesStrategy;
import br.com.jorge.habita.domain.strategy.RendaStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StrategyFactory {

    @Bean
    public DependentesStrategy createDependentesStrategy() {
        return new DependentesStrategy();
    }

    @Bean
    public RendaStrategy createRendaStrategy() {
        return new RendaStrategy();
    }
}
