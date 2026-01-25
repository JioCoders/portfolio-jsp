package com.jiocoders.portfolio.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI().info(new Info().title("JioCoders Portfolio API")
			.version("1.0")
			.description("This is the API documentation for the JioCoders Portfolio project. "
					+ "It provides endpoints for managing user profiles, projects, and other portfolio components.")
			.termsOfService("https://jiocoders.com/terms")
			.contact(new Contact().name("JioCoders Team").email("team@jiocoders.com").url("https://jiocoders.com"))
			.license(new License().name("Apache 2.0").url("https://www.apache.org/licenses/LICENSE-2.0.txt")));
	}

}
