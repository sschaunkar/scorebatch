package com.ets.scorebatch.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class JobService {
	@Autowired
	JobLauncher launcher;
	@Qualifier("firstjob")
	@Autowired
	Job firstjob;
	
	@Qualifier("secondjob")
	@Autowired
	Job secondjob;
	@Async
	public void startJob(String jobName) {
		Map<String,JobParameter<?>> params = new HashMap<>();
		params.put("currentTime",new JobParameter<>(System.currentTimeMillis(), Long.class));
		JobParameters jobparameters = new JobParameters(params);
		try {
			if(jobName.equals("firstjob")) {
				launcher.run(firstjob, jobparameters);	
			}else if(jobName.equals("secondjob")) {	
				launcher.run(secondjob, jobparameters);
			}	
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Exception while Starting job");
		}
	}
}
