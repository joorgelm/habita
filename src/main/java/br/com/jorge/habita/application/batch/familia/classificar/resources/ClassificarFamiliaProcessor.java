package br.com.jorge.habita.application.batch.familia.classificar.resources;

import br.com.jorge.habita.domain.entity.Familia;
import br.com.jorge.habita.domain.service.AnaliseFamiliarService;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.springframework.batch.item.ItemProcessor;

@Builder
public class ClassificarFamiliaProcessor implements ItemProcessor<Familia, Familia> {

    private AnaliseFamiliarService analiseFamiliarService;
    @Override
    public Familia process(@NotNull Familia familia) {
        analiseFamiliarService.atualizarPontuacao(familia);
        return familia;
    }
}
