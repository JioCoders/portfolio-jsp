package com.jiocoders.portfolio.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PortfolioController {

	// Serve JSP page
	@GetMapping({ "/", "/portfolio" })
	public String home() {
		return "index"; // maps to /WEB-INF/jsp/index.jsp
	}

}