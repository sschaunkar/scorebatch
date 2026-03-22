package com.ets.scorebatch.service;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Service;
@Service
public class SecondTask implements Tasklet {

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Second Step Running");
		System.out.println("okok:::: "+chunkContext.getStepContext());
		return RepeatStatus.FINISHED;
	}

}
