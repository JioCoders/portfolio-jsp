package com.jiocoders.portfolio;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.pgvector.PgVectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
// @EnableJpaAuditing
public class Jiocoders2206javaApplication {

	public static void main(String[] args) {
		SpringApplication.run(Jiocoders2206javaApplication.class, args);
		System.out.println("****** Application has been Started ******");
	}

	@PostConstruct
	public void init() {
		System.out.println("✅ Controller initialized successfully!");
	}
	// @Bean
	// public VectorStore vectorStore(JdbcTemplate jdbcTemplate,
	// @Qualifier("openAiEmbeddingModel") EmbeddingModel embeddingModel) {
	// return new PgVectorStore(jdbcTemplate, embeddingModel);
	// }

}
