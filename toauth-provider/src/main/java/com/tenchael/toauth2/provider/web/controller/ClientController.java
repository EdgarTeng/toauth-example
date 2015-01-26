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

import com.tenchael.toauth2.provider.domian.Client;
import com.tenchael.toauth2.provider.service.ClientService;

@Controller
@RequestMapping("/client")
public class ClientController {

	@Autowired
	private ClientService clientService;

	private static final Logger logger = LoggerFactory
			.getLogger(ClientController.class);

	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model) {
		model.addAttribute("clientList", clientService.findAll());
		return "client/list";
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String showCreateForm(Model model) {
		model.addAttribute("client", new Client());
		model.addAttribute(OPERATION, "新增");
		return "client/edit";
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(Client client, RedirectAttributes redirectAttributes) {
		clientService.save(client);
		logger.info("save a client, client information is {}", client);
		redirectAttributes.addFlashAttribute(MESSAGE, "新增成功");
		return "redirect:/client";
	}

	@RequestMapping(value = "/{id}/update", method = RequestMethod.GET)
	public String showUpdateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("client", clientService.get(id));
		model.addAttribute(OPERATION, "修改");
		return "client/edit";
	}

	@RequestMapping(value = "/{id}/update", method = RequestMethod.POST)
	public String update(@PathVariable("id") Long id, Client client,
			RedirectAttributes redirectAttributes) {
		clientService.update(client);
		redirectAttributes.addFlashAttribute(MESSAGE, "修改成功");
		return "redirect:/client";
	}

	@RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
	public String showDeleteForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("client", clientService.get(id));
		model.addAttribute(OPERATION, "删除");
		return "client/edit";
	}

	@RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
	public String delete(@PathVariable("id") Long id,
			RedirectAttributes redirectAttributes) {
		Client deleteClient = clientService.delete(id);
		logger.info("delete client, client info is:{}", deleteClient);
		redirectAttributes.addFlashAttribute(MESSAGE, "删除成功");
		return "redirect:/client";
	}

}
