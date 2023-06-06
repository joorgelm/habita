package br.com.jorge.habita.adapter.factory.distribuicao;

import br.com.jorge.habita.application.repository.DistribuicaoRepository;
import br.com.jorge.habita.application.repository.FamiliaRepository;
import br.com.jorge.habita.application.usecase.distribuicao.RealizarDistribuicaoUsecase;
import br.com.jorge.habita.domain.service.AnaliseFamiliarService;
import br.com.jorge.habita.domain.service.DistribuicaoService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RealizarDistribuicaoFactory {

    @Bean
    public RealizarDistribuicaoUsecase createRealizarDistribuicaoUsecase(
            DistribuicaoRepository distribuicaoRepository,
            FamiliaRepository familiaRepository,
            AnaliseFamiliarService analiseFamiliarService,
            DistribuicaoService distribuicaoService
    ) {
        return RealizarDistribuicaoUsecase
                .builder()
                .familiaRepository(familiaRepository)
                .analiseFamiliarService(analiseFamiliarService)
                .distribuicaoService(distribuicaoService)
                .distribuicaoRepository(distribuicaoRepository)
                .build();
    }
}
