package com.ets.scorebatch.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

//@Service
public class JobSchedular {
	
	@Autowired
	JobLauncher launcher;
	@Qualifier("secondjob")
	@Autowired
	Job secondjob;
	
	//@Scheduled(cron="0 0/1 * 1/1 * ?")
	public void JobStarter() {
		Map<String,JobParameter<?>> params = new HashMap<>();
		params.put("currentTime",new JobParameter<>(System.currentTimeMillis(), Long.class));
		JobParameters jobparameters = new JobParameters(params);
		try {
			JobExecution jobexecution = null;
		    jobexecution = launcher.run(secondjob, jobparameters);	
			System.out.println("jobexecution:: "+jobexecution.getJobId());
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Exception while Starting job");
		}
	}
}
