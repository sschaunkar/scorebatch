package com.ets.scorebatch.controller;

import java.util.List;

import org.springframework.batch.core.launch.JobOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ets.scorebatch.dto.JobRequestParams;
import com.ets.scorebatch.service.JobService;

@RestController
@RequestMapping("/api/job")
public class JobController {
	
	@Autowired
	JobService jobservice;
	
	@Autowired
	JobOperator joboperator;
	
	@PostMapping("/start/{jobName}")
	public String startJob(@PathVariable String jobName, @RequestBody List<JobRequestParams> paramslist) {
		jobservice.startJob(jobName,paramslist);
		return jobName+" started";
	}
	
	@PostMapping("/stop/{jobExecutionId}")
	public String startJob(@PathVariable long jobExecutionId) {
		try {
			joboperator.stop(jobExecutionId);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "Job Stopped";
		
	}
}
