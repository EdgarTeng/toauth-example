package com.tenchael.toauth2.client.web.controller;

import static com.tenchael.toauth2.client.commons.Constants.MESSAGE;
import static com.tenchael.toauth2.client.commons.Settings.MODE;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory
			.getLogger(HomeController.class);

	@RequestMapping("/")
	public String index(Model model) {
		return "redirect:authorize";
	}

	@RequestMapping(value = { "/home" })
	public String home(Model model) {
		String mode = MODE;
		logger.info("mode={}", mode);
		model.addAttribute(MESSAGE, "mode=" + mode);
		return "home";
	}

}
