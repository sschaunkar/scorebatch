package com.ets.scorebatch.listener;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class MyStepListener implements StepExecutionListener {
    @Override
	public void beforeStep(StepExecution stepExecution) {
    	System.out.println("Before Step Name:: "+stepExecution.getStepName());
    	System.out.println("Before job ExecutionContext:: "+stepExecution.getJobExecution().getExecutionContext());
    	System.out.println("Before step ExecutionContext:: "+stepExecution.getExecutionContext());
    	stepExecution.getExecutionContext().put("hobby", "playing");
	}
    @Override
	public ExitStatus afterStep(StepExecution stepExecution) {
    	System.out.println("After Step Name:: "+stepExecution.getStepName());
    	System.out.println("After job ExecutionContext:: "+stepExecution.getJobExecution().getExecutionContext());
    	System.out.println("After step ExecutionContext:: "+stepExecution.getExecutionContext());
    	return null;
	}
}
