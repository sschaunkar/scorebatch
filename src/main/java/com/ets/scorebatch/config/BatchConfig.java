package com.ets.scorebatch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.ets.scorebatch.listener.MyJobListener;
import com.ets.scorebatch.listener.MyStepListener;
import com.ets.scorebatch.service.SecondTask;

@Configuration
public class BatchConfig {
	
	@Autowired
	MyJobListener joblistener;
	@Autowired
	MyStepListener steplistener;
	@Autowired
	SecondTask secondTask;

    @Bean
    public Job job(JobRepository jobRepository, Step step,Step secondStep) {
        return new JobBuilder("demoJob", jobRepository)
        		.incrementer(new RunIdIncrementer())
        		.listener(joblistener)
                .start(step)
                .next(secondStep)
                .build();
    }

    @Bean
    public Step step(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("demoStep", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("🔥 Batch Job Executed Successfully!");
                    return RepeatStatus.FINISHED;
                }, transactionManager).listener(steplistener)
                .build();
    }
    
    @Bean
    public Step secondStep(JobRepository jobRepository,
                           PlatformTransactionManager transactionManager,
                           SecondTask secondTask) {

        return new StepBuilder("secondStep", jobRepository)
                .tasklet(secondTask, transactionManager)
                .listener(steplistener)
                .build();
    }
}
