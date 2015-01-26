package com.tenchael.toauth2.provider.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.tenchael.toauth2.provider.commons.Settings.*;

@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory
			.getLogger(HomeController.class);

	@RequestMapping("/")
	public String index(Model model) {
		return "index";
	}

	@RequestMapping(value = { "/home" })
	public String home(Model model) {
		String mode = MODE;
		logger.info("mode={}", mode);
		model.addAttribute("mode", mode);
		return "home";
	}

}
