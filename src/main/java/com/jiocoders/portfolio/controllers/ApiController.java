package com.jiocoders.portfolio.controllers;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

    // Serve JSON response
    // GET API: /api/hello
    @GetMapping("/api/hello")
    public Map<String, String> helloApi() {
        return Map.of("message", "Hi, I'm Jiocoders team");
    }

    // POST API: /api/hello
    @PostMapping("/api/hello")
    public Map<String, String> helloPost(@RequestBody Map<String, String> body) {
        String name = body.getOrDefault("name", "friend");
        return Map.of("message", "Hi, I'm " + name + " from Jiocoders team");
    }
}