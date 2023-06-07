package br.com.jorge.habita.application.batch.familia.classificar.resources;

import br.com.jorge.habita.domain.entity.Familia;
import lombok.Builder;
import org.springframework.batch.item.data.RepositoryItemReader;

@Builder
public class FamiliaReader extends RepositoryItemReader<Familia> {
}
