package com.ets.scorebatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ScorebatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScorebatchApplication.class, args);
	}

}
