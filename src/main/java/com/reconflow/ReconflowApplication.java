package com.reconflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ReconflowApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReconflowApplication.class, args);
	}

}
