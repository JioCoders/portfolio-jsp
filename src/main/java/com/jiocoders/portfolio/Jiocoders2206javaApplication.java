package com.jiocoders.portfolio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class Jiocoders2206javaApplication{// extends SpringBootServletInitializer implements  ServletContextListener {
	public static void main(String[] args) {
		SpringApplication.run(Jiocoders2206javaApplication.class, args);
		System.out.println("****** Application has been Started ******");
	}
	
    // @Override
    // protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
    //     System.out.println("****** Application has been Started via WAR ******");
    //     return builder.sources(Jiocoders2206javaApplication.class);
    // }

    // @Override
    // public void contextInitialized(ServletContextEvent sce) {
    //     System.out.println("🔥 ServletContext initialized. Spring Boot is starting...");
    // }
	
	@PostConstruct
	public void init() {
		System.out.println("PortfolioController initialized success");
	}
}
 