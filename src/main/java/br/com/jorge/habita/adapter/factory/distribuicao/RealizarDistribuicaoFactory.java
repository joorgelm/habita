package br.com.jorge.habita.adapter.factory.distribuicao;

import br.com.jorge.habita.adapter.repository.DistribuicaoRepository;
import br.com.jorge.habita.adapter.repository.FamiliaRepository;
import br.com.jorge.habita.application.usecase.distribuicao.RealizarDistribuicaoUsecase;
import br.com.jorge.habita.domain.strategy.CriterioAvalicaoStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class RealizarDistribuicaoFactory {

    @Bean
    public RealizarDistribuicaoUsecase createRealizarDistribuicaoUsecase(DistribuicaoRepository distribuicaoRepository, FamiliaRepository familiaRepository, List<CriterioAvalicaoStrategy> criterioAvalicaoStrategyList) {
        return RealizarDistribuicaoUsecase
                .builder()
                .criterioAvalicaoStrategies(criterioAvalicaoStrategyList)
                .familiaRepository(familiaRepository)
                .distribuicaoRepository(distribuicaoRepository)
                .build();
    }
}
