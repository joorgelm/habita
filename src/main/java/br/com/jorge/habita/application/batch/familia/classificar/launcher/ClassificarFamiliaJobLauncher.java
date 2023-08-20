package br.com.jorge.habita.application.batch.familia.classificar.launcher;


import jakarta.validation.constraints.NotNull;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.support.TaskExecutorJobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ClassificarFamiliaJobLauncher extends TaskExecutorJobLauncher {

    public ClassificarFamiliaJobLauncher(JobRepository jobRepository) {
        this.setJobRepository(jobRepository);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public @NotNull JobExecution run(@NotNull Job job, @NotNull JobParameters jobParameters) throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
        return super.run(job, jobParameters);
    }
}
