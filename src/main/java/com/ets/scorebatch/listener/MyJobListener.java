package com.ets.scorebatch.listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class MyJobListener implements JobExecutionListener {

	@Override
    public void beforeJob(JobExecution jobExecution) {
        System.out.println("🚀 Job Started: " + jobExecution.getJobInstance().getJobName());
        System.out.println("🚀 Job Params: " + jobExecution.getJobParameters());
    }
	@Override
	public void afterJob(JobExecution jobExecution) {
	    System.out.println("✅ Job Finished: " + jobExecution.getStatus());
	    System.out.println("🚀 Job Params: " + jobExecution.getJobParameters());
	}
}
