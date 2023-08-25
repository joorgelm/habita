package br.com.jorge.habita.application.batch.familia.classificar.dispatcher;

import br.com.jorge.habita.application.batch.familia.classificar.launcher.ClassificarFamiliaJobLauncher;
import br.com.jorge.habita.domain.exception.ClassificacaoException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ClassificarFamiliaDispatcherImpl implements ClassificarFamiliaDispatcher {

    private final ClassificarFamiliaJobLauncher jobLauncher;

    private final Job classificarFamilia;

    public ClassificarFamiliaDispatcherImpl(ClassificarFamiliaJobLauncher jobLauncher, Job classificarFamilia) {
        this.jobLauncher = jobLauncher;
        this.classificarFamilia = classificarFamilia;
    }

    @Override
    public void executarJob() {
        try {
            this.atualizarPontuacaoDasFamiliasCadastradas();
        } catch (JobExecutionException e) {
            throw new ClassificacaoException();
        }
    }

    private void atualizarPontuacaoDasFamiliasCadastradas() throws JobInstanceAlreadyCompleteException,
            JobExecutionAlreadyRunningException,
            JobParametersInvalidException,
            JobRestartException
    {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLocalDateTime("moment", LocalDateTime.now())
                .toJobParameters();
        jobLauncher.run(classificarFamilia, jobParameters);
    }
}
