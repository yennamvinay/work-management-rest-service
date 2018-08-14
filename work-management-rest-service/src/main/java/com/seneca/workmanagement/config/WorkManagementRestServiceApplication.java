package com.seneca.workmanagement.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { "com.seneca.workmanagement" })
public class WorkManagementRestServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(WorkManagementRestServiceApplication.class, args);
	}
}
