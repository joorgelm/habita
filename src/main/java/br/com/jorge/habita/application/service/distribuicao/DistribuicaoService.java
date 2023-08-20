package br.com.jorge.habita.application.service.distribuicao;

import br.com.jorge.habita.application.service.distribuicao.io.DistribuicaoOutput;
import br.com.jorge.habita.domain.entity.Distribuicao;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;

public interface DistribuicaoService {

    Distribuicao gerarRegistroDistribuicao();

    DistribuicaoOutput realizarDistribuicaoDeCasas(int qtdCasas) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException;
}
