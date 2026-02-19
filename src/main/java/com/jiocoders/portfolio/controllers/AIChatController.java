package com.jiocoders.portfolio.controllers;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${jio.api.prefix}/ai")
public class AIChatController {

	private ChatClient openAiChatClient;

	private ChatClient ollamaChatClient;

	public AIChatController(@Qualifier("openAiChatClient") ChatClient openAiChatClient,
			@Qualifier("ollamaChatClient") ChatClient ollamaChatClient) {
		System.out.println(openAiChatClient.getClass().getName());
		System.out.println(ollamaChatClient.getClass().getName());
		this.openAiChatClient = openAiChatClient;
		this.ollamaChatClient = ollamaChatClient;
	}

	@GetMapping("/chat-ollama")
	public ResponseEntity<String> chatOllama(@RequestParam(value = "q", required = true) @NonNull String q) {
		var resultResponse = this.ollamaChatClient.prompt(q).call().content();

		return ResponseEntity.ok(resultResponse);
	}

	@GetMapping("/chat-openai")
	public ResponseEntity<String> chatOpenAi(@RequestParam(value = "q", required = true) @NonNull String q) {
		var resultResponse = this.openAiChatClient.prompt(q).call().content();

		return ResponseEntity.ok(resultResponse);
	}

}