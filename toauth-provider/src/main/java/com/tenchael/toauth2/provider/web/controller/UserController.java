package com.tenchael.toauth2.provider.web.controller;

import static com.tenchael.toauth2.provider.commons.Constants.MESSAGE;
import static com.tenchael.toauth2.provider.commons.Constants.OPERATION;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tenchael.toauth2.provider.domian.User;
import com.tenchael.toauth2.provider.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	private static final Logger logger = LoggerFactory
			.getLogger(UserController.class);

	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model) {
		model.addAttribute("userList", userService.findAll());
		return "user/list";
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String showCreateForm(Model model) {
		model.addAttribute("user", new User());
		model.addAttribute(OPERATION, "新增");
		return "user/edit";
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(User user, RedirectAttributes redirectAttributes) {
		userService.save(user);
		logger.info("save a user, user infomation is {}", user);
		redirectAttributes.addFlashAttribute(MESSAGE, "新增成功");
		return "redirect:/user";
	}

	@RequestMapping(value = "/{id}/update", method = RequestMethod.GET)
	public String showUpdateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("user", userService.get(id));
		model.addAttribute(OPERATION, "修改");
		return "user/edit";
	}

	@RequestMapping(value = "/{id}/update", method = RequestMethod.POST)
	public String update(User user, RedirectAttributes redirectAttributes) {
		userService.update(user);
		logger.info("update user info as {}", user);
		redirectAttributes.addFlashAttribute(MESSAGE, "修改成功");
		return "redirect:/user";
	}

	@RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
	public String showDeleteForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("user", userService.get(id));
		model.addAttribute(OPERATION, "删除");
		return "user/edit";
	}

	@RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
	public String delete(@PathVariable("id") Long id,
			RedirectAttributes redirectAttributes) {
		User deleteUser = userService.delete(id);
		logger.info("delete user, user info is: {}", deleteUser);
		redirectAttributes.addFlashAttribute(MESSAGE, "删除成功");
		return "redirect:/user";
	}

	@RequestMapping(value = "/{id}/changePassword", method = RequestMethod.GET)
	public String showChangePasswordForm(@PathVariable("id") Long id,
			Model model) {
		model.addAttribute("user", userService.get(id));
		model.addAttribute(OPERATION, "修改密码");
		return "user/changePassword";
	}

	@RequestMapping(value = "/{id}/changePassword", method = RequestMethod.POST)
	public String changePassword(@PathVariable("id") Long id,
			String newPassword, RedirectAttributes redirectAttributes) {
		userService.modifyPassword(id, newPassword);
		redirectAttributes.addFlashAttribute(MESSAGE, "修改密码成功");
		return "redirect:/user";
	}

}
