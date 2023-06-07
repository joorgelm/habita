package br.com.jorge.habita.application.batch.familia.classificar.resources;

import br.com.jorge.habita.domain.entity.Familia;
import lombok.Builder;
import org.springframework.batch.item.data.RepositoryItemWriter;

@Builder
public class FamiliaWriter extends RepositoryItemWriter<Familia> {}
