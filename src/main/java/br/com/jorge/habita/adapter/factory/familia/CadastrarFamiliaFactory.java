package br.com.jorge.habita.adapter.factory.familia;

import br.com.jorge.habita.adapter.repository.FamiliaRepository;
import br.com.jorge.habita.adapter.repository.MembroRepository;
import br.com.jorge.habita.application.usecase.familia.cadastrar.CadastrarFamiliaUsecase;
import br.com.jorge.habita.domain.strategy.CriterioAvalicaoStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class CadastrarFamiliaFactory {

    @Bean
    public CadastrarFamiliaUsecase createCadastrarFamiliaUsecase(FamiliaRepository familiaRepository,
                                                                 List<CriterioAvalicaoStrategy> criterioAvalicaoStrategies,
                                                                 MembroRepository membroRepository) {
        return CadastrarFamiliaUsecase
                .builder()
                .criterioAvalicaoStrategies(criterioAvalicaoStrategies)
                .membroRepository(membroRepository)
                .familiaRepository(familiaRepository)
                .build();
    }
}
