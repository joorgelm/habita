package br.com.jorge.habita.application.service.familia;

import br.com.jorge.habita.application.batch.familia.classificar.launcher.ClassificarFamiliaJobLauncher;
import br.com.jorge.habita.application.repository.FamiliaRepository;
import br.com.jorge.habita.application.service.familia.io.FamiliaInput;
import br.com.jorge.habita.application.service.membro.MembroService;
import br.com.jorge.habita.domain.entity.Distribuicao;
import br.com.jorge.habita.domain.entity.Familia;
import br.com.jorge.habita.domain.exception.DistribuicaoIncompletaException;
import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FamiliaServiceImpl implements FamiliaService {

    private final FamiliaRepository familiaRepository;

    private final MembroService membroService;

    private final ClassificarFamiliaJobLauncher jobLauncher;

    private final Job classificarFamilia;

    public FamiliaServiceImpl(FamiliaRepository familiaRepository,
                              MembroService membroService,
                              ClassificarFamiliaJobLauncher jobLauncher,
                              Job classificarFamilia
    ) {
        this.familiaRepository = familiaRepository;
        this.membroService = membroService;
        this.jobLauncher = jobLauncher;
        this.classificarFamilia = classificarFamilia;
    }

    @Transactional
    @Override
    public void cadastrarFamilia(FamiliaInput familiaInput) {
        Familia familia = familiaRepository.save(Familia.of(familiaInput));
        membroService.cadastrarListaDeMembros(familiaInput.getMembros(), familia);
    }

    private void atualizarPontuacaoDasFamiliasCadastradas() throws  JobInstanceAlreadyCompleteException,
                                                                    JobExecutionAlreadyRunningException,
                                                                    JobParametersInvalidException,
                                                                    JobRestartException
    {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLocalDateTime("moment", LocalDateTime.now())
                .toJobParameters();
        jobLauncher.run(classificarFamilia, jobParameters);
    }

    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    @SneakyThrows
    @Override
    public List<Familia> buscarFamiliasContempladas(Integer quantidadeCasas) {
        atualizarPontuacaoDasFamiliasCadastradas();
        return buscarFamiliasOrdernadasPelaPontuacao(quantidadeCasas);
    }

    @Override
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
