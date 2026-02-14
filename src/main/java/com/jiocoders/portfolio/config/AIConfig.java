package com.jiocoders.portfolio.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;

@Configuration
public class AIConfig {

	@Bean(name = "openAiChatClient")
	public ChatClient openAiChatModel(@NonNull OpenAiChatModel chatModel) {
		return ChatClient.builder(chatModel).build();
	}

	@Bean(name = "ollamaChatClient")
	public ChatClient ollamaChatModel(@NonNull OllamaChatModel chatModel) {
		return ChatClient.builder(chatModel).build();
	}

}