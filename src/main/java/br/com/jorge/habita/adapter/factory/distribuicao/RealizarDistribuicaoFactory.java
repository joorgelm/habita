package br.com.jorge.habita.adapter.factory.distribuicao;

import br.com.jorge.habita.adapter.repository.FamiliaRepository;
import br.com.jorge.habita.adapter.repository.DistribuicaoRepository;
import br.com.jorge.habita.application.usecase.distribuicao.RealizarDistribuicaoUsecase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RealizarDistribuicaoFactory {

    @Bean
    public RealizarDistribuicaoUsecase createRealizarDistribuicaoUsecase(DistribuicaoRepository DistribuicaoRepository, FamiliaRepository familiaRepository) {
        return RealizarDistribuicaoUsecase
                .builder()
                .familiaRepository(familiaRepository)
                .distribuicaoRepository(DistribuicaoRepository)
                .build();
    }
}
