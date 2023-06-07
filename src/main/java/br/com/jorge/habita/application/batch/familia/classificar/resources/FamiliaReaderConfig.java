package br.com.jorge.habita.application.batch.familia.classificar.resources;

import br.com.jorge.habita.application.repository.FamiliaRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;

import java.util.Map;

@Configuration
public class FamiliaReaderConfig {
    @Bean
    public FamiliaReader familiaItemReader(FamiliaRepository familiaRepository) {

        FamiliaReader familiaReader = FamiliaReader.builder().build();
        familiaReader.setRepository(familiaRepository);
        familiaReader.setSort(Map.of("id", Sort.Direction.ASC));
        familiaReader.setMethodName("findAll");

        return familiaReader;
    }
}
