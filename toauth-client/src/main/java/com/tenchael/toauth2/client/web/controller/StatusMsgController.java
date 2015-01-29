package com.tenchael.toauth2.client.web.controller;

import static com.tenchael.toauth2.client.commons.Constants.LIST;
import static com.tenchael.toauth2.client.commons.Constants.MESSAGE;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tenchael.toauth2.client.commons.HttpUtils;
import com.tenchael.toauth2.client.domain.Message;
import com.tenchael.toauth2.client.service.MessageService;
import com.tenchael.toauth2.client.service.UserInfoService;

@Controller
public class StatusMsgController {

	private static final Logger logger = LoggerFactory
			.getLogger(StatusMsgController.class);
	@Autowired
	private UserInfoService userInfoService;

	@Autowired
	private MessageService messageService;

	@RequestMapping(value = { "create" })
	public String createForm(Model model) {
		Message statusMessage = new Message();
		model.addAttribute("statusMessage", statusMessage);
		return "form";
	}

	@RequestMapping(value = { "create" }, method = RequestMethod.POST)
	public String create(Message statusMessage, Model model,
			HttpServletRequest request) {
		logger.info("sync a message, the content is: {}",
				statusMessage.getContent());
		String sessionKey = HttpUtils.getSessionKey(request);
		String msg = messageService.publish(sessionKey, statusMessage);
		model.addAttribute(MESSAGE, msg);
		return "home";
	}

	@RequestMapping(value = { "getMessages" })
	public String getMessages(Model model, HttpServletRequest request) {
		String sessionKey = HttpUtils.getSessionKey(request);
		List<Message> list = messageService.getMessages(sessionKey);
		model.addAttribute(LIST, list);
		return "list";
	}

}
