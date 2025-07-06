package com.jiocoders.portfolio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class Jiocoders2206javaApplication {
    public static void main(String[] args) {
		SpringApplication.run(Jiocoders2206javaApplication.class, args);
		System.out.println("****** Application has been Started ******");
	}
	
	@PostConstruct
	public void init() {
		System.out.println("✅ Controller initialized successfully!");
	}
}
 