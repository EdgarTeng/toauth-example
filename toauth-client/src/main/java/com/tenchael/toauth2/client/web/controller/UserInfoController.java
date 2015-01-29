package com.tenchael.toauth2.client.web.controller;

import static com.tenchael.toauth2.client.commons.Constants.MESSAGE;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tenchael.toauth2.client.commons.HttpUtils;
import com.tenchael.toauth2.client.service.UserInfoService;

@Controller
public class UserInfoController {

	private static final Logger logger = LoggerFactory
			.getLogger(UserInfoController.class);
	@Autowired
	private UserInfoService userInfoService;

	@RequestMapping(value = { "getUserInfo" })
	public String getUserInfo(Model model, HttpServletRequest request) {
		String sessionKey = HttpUtils.getSessionKey(request);
		String userInfo = userInfoService.getUserInfo(sessionKey);
		logger.info("user info is: {}", userInfo);
		model.addAttribute(MESSAGE, userInfo);
		return "home";
	}

}
