package org.springframework.batch.samples.simplejob;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.batch.samples.common.DataSourceConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.support.JdbcTransactionManager;

/**
 * @author phongpq
 */
@Configuration
@EnableBatchProcessing
@Import(DataSourceConfiguration.class)
public class SimpleJobConfiguration {

    @Bean
    public Step step(JobRepository jobRepository, JdbcTransactionManager transactionManager) {
        return new StepBuilder("step", jobRepository).tasklet((contribution, chunkContext) -> {
            System.out.println("process step 01");
            return RepeatStatus.FINISHED;
        }, transactionManager).build();
    }

    @Bean
    public Step step01(JobRepository jobRepository, JdbcTransactionManager transactionManager) {
        return new StepBuilder("step01", jobRepository).tasklet((contribution, chunkContext) -> {
            System.out.println("process step 02");
            return RepeatStatus.FINISHED;
        }, transactionManager).build();
    }

    @Bean
    public Job job(JobRepository jobRepository, Step step, Step step01) {
        return new JobBuilder("job", jobRepository)
                .start(step)
                .next(step01)
                .build();
    }
}
