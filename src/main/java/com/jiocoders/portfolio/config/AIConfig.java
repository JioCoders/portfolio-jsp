package com.jiocoders.portfolio.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.OllamaEmbeddingModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.lang.NonNull;

@Configuration
public class AIConfig {

	// @Bean
	// public EmbeddingModel primaryEmbeddingModel(GoogleAiEmbeddingModel
	// googleAiEmbeddingModel) {
	// return googleAiEmbeddingModel;
	// }

	@Bean
	@Primary
	public EmbeddingModel embeddingModel(OllamaEmbeddingModel ollamaEmbeddingModel) {
		return ollamaEmbeddingModel;
	}

	@Bean(name = "openAiChatClient")
	public ChatClient openAiChatModel(@NonNull OpenAiChatModel chatModel) {
		return ChatClient.builder(chatModel).build();
	}

	@Bean(name = "ollamaChatClient")
	public ChatClient ollamaChatModel(@NonNull OllamaChatModel chatModel) {
		return ChatClient.builder(chatModel).build();
	}

}