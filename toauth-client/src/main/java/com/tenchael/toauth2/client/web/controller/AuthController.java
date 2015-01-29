package com.tenchael.toauth2.client.web.controller;

import static com.tenchael.toauth2.client.commons.Settings.CLIENT_HOME_URL;
import static com.tenchael.toauth2.client.commons.Settings.CLIENT_ID;
import static com.tenchael.toauth2.client.commons.Settings.CLIENT_REDIRECT_URL;
import static com.tenchael.toauth2.client.commons.Settings.PROVIDER_AUTHORIZE_URL;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tenchael.toauth2.client.commons.HttpUtils;
import com.tenchael.toauth2.client.service.OAuthService;

@Controller
public class AuthController {

	private static final Logger logger = LoggerFactory
			.getLogger(AuthController.class);
	@Autowired
	private OAuthService oAuthService;

	@RequestMapping(value = { "extractCode/{sessionKey}" })
	public String extractCode(@PathVariable("sessionKey") String sessionKey,
			String code, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("authorization code is: {}", code);
		oAuthService.addAuthCode(sessionKey, code);
		if (oAuthService.checkAuthCode(sessionKey)) {
			oAuthService.authorize(sessionKey);
			logger.info("get access token success");
		} else {
			logger.error("authorization code request failed");
		}
		String redirect = CLIENT_HOME_URL;
		return "redirect:" + redirect;
	}

	@RequestMapping(value = { "/authorize" })
	public void authorize(Model model, HttpServletRequest request,
			HttpServletResponse response) {
		String sessionKey = HttpUtils.getSessionKey(request);
		logger.info("sessionKey is: {}", sessionKey);
		String redirectUrl = CLIENT_REDIRECT_URL + "/" + sessionKey;
		Map<String, String> params = new HashMap<String, String>();
		params.put("client_id", CLIENT_ID);
		params.put("response_type", "code");
		params.put("redirect_uri", redirectUrl);
		String authRequestUrl = HttpUtils.jointParams(PROVIDER_AUTHORIZE_URL,
				params);

		try {
			logger.info("request authorization code, request url is: {}",
					authRequestUrl);
			response.sendRedirect(authRequestUrl);
		} catch (Exception e) {
			logger.error("build auth code request failed, error info: {}",
					e.getMessage());
		}
	}
}
