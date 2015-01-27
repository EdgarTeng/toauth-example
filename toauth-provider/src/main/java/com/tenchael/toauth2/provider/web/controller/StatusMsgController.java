package com.tenchael.toauth2.provider.web.controller;

import static com.tenchael.toauth2.provider.commons.Constants.MESSAGE;
import static com.tenchael.toauth2.provider.commons.Constants.OPERATION;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tenchael.toauth2.provider.domian.StatusMsg;
import com.tenchael.toauth2.provider.domian.User;
import com.tenchael.toauth2.provider.service.StatusMsgService;
import com.tenchael.toauth2.provider.service.UserService;

@Controller
@RequestMapping("/statusMsg")
public class StatusMsgController {

	@Autowired
	private StatusMsgService statusMsgService;

	@Autowired
	private UserService userService;

	private static final Logger logger = LoggerFactory
			.getLogger(StatusMsgController.class);

	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model) {
		model.addAttribute("statusMsgList", statusMsgService.findAllVisible());
		return "statusMsg/list";
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String showCreateForm(Model model) {
		model.addAttribute("statusMsg", new StatusMsg());
		model.addAttribute(OPERATION, "新增");
		return "statusMsg/edit";
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(StatusMsg statusMsg,
			RedirectAttributes redirectAttributes) {
		User user = userService.getCurrentUser();
		// 如果用户没有登录，跳转到登陆页面
		if (user == null) {
			return "oauth2login";
		}
		statusMsg.setUser(user);
		statusMsgService.save(statusMsg);
		logger.info("publish a statusMsg, statusMsg content is {}",
				statusMsg.getContent());
		redirectAttributes.addFlashAttribute(MESSAGE, "新增成功");
		return "redirect:/statusMsg";
	}

	@RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
	public String showDeleteForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("statusMsg", statusMsgService.findOne(id));
		model.addAttribute(OPERATION, "删除");
		return "statusMsg/edit";
	}

	@RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
	public String delete(@PathVariable("id") Long id,
			RedirectAttributes redirectAttributes) {
		StatusMsg statusMsg = statusMsgService.delete(id);
		logger.info("delete a statusMsg, statusMsg content is: {}",
				statusMsg.getContent());
		redirectAttributes.addFlashAttribute(MESSAGE, "删除成功");
		return "redirect:/statusMsg";
	}

}
