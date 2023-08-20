package br.com.jorge.habita.application.service.distribuicao;

import br.com.jorge.habita.application.repository.DistribuicaoRepository;
import br.com.jorge.habita.application.service.distribuicao.io.DistribuicaoOutput;
import br.com.jorge.habita.application.service.familia.FamiliaService;
import br.com.jorge.habita.application.service.familia.io.FamiliaOutput;
import br.com.jorge.habita.domain.entity.Distribuicao;
import br.com.jorge.habita.domain.entity.Familia;
import jakarta.transaction.Transactional;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DistribuicaoServiceImpl implements DistribuicaoService {

    private final FamiliaService familiaService;
    private final DistribuicaoRepository distribuicaoRepository;

    public DistribuicaoServiceImpl(FamiliaService familiaService, DistribuicaoRepository distribuicaoRepository) {
        this.familiaService = familiaService;
        this.distribuicaoRepository = distribuicaoRepository;
    }

    @Transactional
    @Override
    public DistribuicaoOutput realizarDistribuicaoDeCasas(int qtdCasas) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        List<Familia> familiasContempladas = this.familiaService.buscarFamiliasContempladas(qtdCasas);
        Distribuicao distribuicao = gerarRegistroDistribuicao();
        List<FamiliaOutput> familiaOutputList = homologarDistribuicao(familiasContempladas, distribuicao);

        return distribuicao.toOutput(familiaOutputList);
    }

    private List<FamiliaOutput> homologarDistribuicao(List<Familia> familiasContempladas, Distribuicao distribuicao) {
        return familiasContempladas.stream()
                .peek(familia -> this.familiaService.homologarFamiliaContemplada(familia, distribuicao))
                .map(Familia::toOutput)
                .toList();
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public Distribuicao gerarRegistroDistribuicao() {
        return distribuicaoRepository.save(new Distribuicao());
    }
}
