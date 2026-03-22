package com.ets.scorebatch.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ets.scorebatch.service.JobService;

@RestController
@RequestMapping("/api/job")
public class JobController {
	
	@Autowired
	JobService jobservice;
	
	@GetMapping("/start/{jobName}")
	public String startJob(@PathVariable String jobName) {
		jobservice.startJob(jobName);
		return jobName+" started";
	}
}
