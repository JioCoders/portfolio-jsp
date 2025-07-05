package com.jiocoders.portfolio;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

@SpringBootApplication
public class Jiocoders2206javaApplication extends SpringBootServletInitializer implements  ServletContextListener {
	// public static void main(String[] args) {
	// 	SpringApplication.run(Jiocoders2206javaApplication.class, args);
	// 	System.out.println("****** Application has been Started ******");
	// }
	
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        System.out.println("****** Application has been Started via WAR ******");
        return builder.sources(Jiocoders2206javaApplication.class);
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("🔥 ServletContext initialized. Spring Boot is starting...");
    }
	
	@PostConstruct
	public void init() {
		System.out.println("PortfolioController initialized success");
	}
}
 