package br.com.jorge.habita.application.service.familia;

import br.com.jorge.habita.application.batch.familia.classificar.dispatcher.ClassificarFamiliaDispatcher;
import br.com.jorge.habita.application.repository.FamiliaRepository;
import br.com.jorge.habita.application.service.familia.io.FamiliaInput;
import br.com.jorge.habita.application.service.membro.MembroService;
import br.com.jorge.habita.domain.entity.Distribuicao;
import br.com.jorge.habita.domain.entity.Familia;
import br.com.jorge.habita.domain.exception.DistribuicaoIncompletaException;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Positive;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FamiliaServiceImpl implements FamiliaService {

    private final FamiliaRepository familiaRepository;

    private final MembroService membroService;

    private final ClassificarFamiliaDispatcher classificarFamiliaDispatcher;

    public FamiliaServiceImpl(FamiliaRepository familiaRepository,
                              MembroService membroService,
                              ClassificarFamiliaDispatcher classificarFamiliaDispatcher
    ) {
        this.familiaRepository = familiaRepository;
        this.membroService = membroService;
        this.classificarFamiliaDispatcher = classificarFamiliaDispatcher;
    }

    @Transactional
    @Override
    public void cadastrarFamilia(FamiliaInput familiaInput) {
        Familia familia = familiaRepository.save(Familia.of(familiaInput));
        membroService.cadastrarListaDeMembros(familiaInput.getMembros(), familia);
    }

    @Transactional
    @Override
    public List<Familia> buscarFamiliasContempladas(@Positive Integer quantidadeCasas) {
        classificarFamiliaDispatcher.executarJob();
        return buscarFamiliasOrdernadasPelaPontuacao(quantidadeCasas);
    }

    @Override
    @Transactional
    public void homologarFamiliaContemplada(Familia familia, Distribuicao distribuicao) {
        familia.setDistribuicao(distribuicao);
        familiaRepository.save(familia);
    }

    private List<Familia> buscarFamiliasOrdernadasPelaPontuacao(Integer quantidadeCasas) {
        List<Familia> familiasContempladas = familiaRepository.findByDistribuicaoIsNullOrderByPontuacaoDesc(quantidadeCasas);

        if (familiasContempladas.size() < quantidadeCasas)
            throw new DistribuicaoIncompletaException();
        return familiasContempladas;
    }
}
