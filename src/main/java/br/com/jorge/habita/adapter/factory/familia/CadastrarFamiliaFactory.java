package br.com.jorge.habita.adapter.factory.familia;

import br.com.jorge.habita.application.repository.FamiliaRepository;
import br.com.jorge.habita.application.repository.MembroRepository;
import br.com.jorge.habita.application.usecase.familia.cadastrar.CadastrarFamiliaUsecase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CadastrarFamiliaFactory {

    @Bean
    public CadastrarFamiliaUsecase createCadastrarFamiliaUsecase(FamiliaRepository familiaRepository, MembroRepository membroRepository) {
        return CadastrarFamiliaUsecase
                .builder()
                .membroRepository(membroRepository)
                .familiaRepository(familiaRepository)
                .build();
    }
}
