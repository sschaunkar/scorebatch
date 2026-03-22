package com.ets.scorebatch.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.ets.scorebatch.dto.JobRequestParams;

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
	public void startJob(String jobName, List<JobRequestParams> paramslist) {
		Map<String,JobParameter<?>> params = new HashMap<>();
		params.put("currentTime",new JobParameter<>(System.currentTimeMillis(), Long.class));
		paramslist.stream().forEach(param -> {
			params.put(param.getParamKey(),new JobParameter<>(param.getParamValue(),String.class));
		});
		JobParameters jobparameters = new JobParameters(params);
		try {
			JobExecution jobexecution = null;
			if(jobName.equals("firstjob")) {
				jobexecution = launcher.run(firstjob, jobparameters);	
			}else if(jobName.equals("secondjob")) {	
				jobexecution = launcher.run(secondjob, jobparameters);
			}
			System.out.println("jobexecution:: "+jobexecution.getJobId());
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Exception while Starting job");
		}
	}
}
