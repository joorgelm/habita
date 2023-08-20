package br.com.jorge.habita.application.service.familia;

import br.com.jorge.habita.application.service.familia.io.FamiliaInput;
import br.com.jorge.habita.domain.entity.Distribuicao;
import br.com.jorge.habita.domain.entity.Familia;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;

import java.util.List;

public interface FamiliaService {

    void cadastrarFamilia(FamiliaInput familiaInput);

    List<Familia> buscarFamiliasContempladas(Integer quantidadeCasas) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException;

    void homologarFamiliaContemplada(Familia familia, Distribuicao distribuicao);
}
