package br.com.jorge.habita.application.batch.familia.classificar.resources;

import br.com.jorge.habita.domain.entity.Familia;
import br.com.jorge.habita.domain.strategy.CriterioAvalicaoStrategy;
import jakarta.validation.constraints.NotNull;
import org.springframework.batch.item.ItemProcessor;

import java.util.List;

public class ClassificarFamiliaProcessor implements ItemProcessor<Familia, Familia> {

    private final List<CriterioAvalicaoStrategy> criterioAvalicaoStrategies;

    public ClassificarFamiliaProcessor(List<CriterioAvalicaoStrategy> criterioAvalicaoStrategies) {
        this.criterioAvalicaoStrategies = criterioAvalicaoStrategies;
    }

    @Override
    public Familia process(@NotNull Familia familia) {
        familia.atualizarPontuacao(criterioAvalicaoStrategies);
        return familia;
    }
}
