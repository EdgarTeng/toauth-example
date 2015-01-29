package com.tenchael.toauth2.provider.web.controller;

import static com.tenchael.toauth2.provider.commons.Constants.MESSAGE;
import static com.tenchael.toauth2.provider.commons.Constants.OPERATION;
import static com.tenchael.toauth2.provider.commons.Constants.RESOURCE_SERVER_NAME;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.ParameterStyle;
import org.apache.oltu.oauth2.common.utils.OAuthUtils;
import org.apache.oltu.oauth2.rs.request.OAuthAccessResourceRequest;
import org.apache.oltu.oauth2.rs.response.OAuthRSResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tenchael.toauth2.provider.commons.EntityUtils;
import com.tenchael.toauth2.provider.commons.HttpUtils;
import com.tenchael.toauth2.provider.commons.Jsonable;
import com.tenchael.toauth2.provider.domian.StatusMsg;
import com.tenchael.toauth2.provider.domian.User;
import com.tenchael.toauth2.provider.service.OAuthService;
import com.tenchael.toauth2.provider.service.StatusMsgService;
import com.tenchael.toauth2.provider.service.UserService;

@Controller
@RequestMapping("/statusMsg")
public class StatusMsgController {

	@Autowired
	private StatusMsgService statusMsgService;

	@Autowired
	private UserService userService;

	@Autowired
	private OAuthService oAuthService;

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

	@RequestMapping(value = { "/pull" }, method = RequestMethod.POST)
	public HttpEntity pull(HttpServletRequest request)
			throws OAuthSystemException {
		try {

			// 构建OAuth资源请求
			OAuthAccessResourceRequest oauthRequest = new OAuthAccessResourceRequest(
					request, ParameterStyle.QUERY);
			// 获取Access Token
			String accessToken = oauthRequest.getAccessToken();
			// request.

			// 验证Access Token
			if (!oAuthService.checkAccessToken(accessToken)) {
				// 如果不存在/过期了，返回未验证错误，需重新验证
				OAuthResponse oauthResponse = OAuthRSResponse
						.errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
						.setRealm(RESOURCE_SERVER_NAME)
						.setError(OAuthError.ResourceResponse.INVALID_TOKEN)
						.buildHeaderMessage();

				HttpHeaders headers = new HttpHeaders();
				headers.add(OAuth.HeaderType.WWW_AUTHENTICATE, oauthResponse
						.getHeader(OAuth.HeaderType.WWW_AUTHENTICATE));
				return new ResponseEntity<HttpHeaders>(headers,
						HttpStatus.UNAUTHORIZED);
			}
			// 返回用户名
			String username = oAuthService
					.getUsernameByAccessToken(accessToken);

			User user = userService.findByUsername(username);

			String bodyContent = null;
			String retJson = null;
			try {
				bodyContent = HttpUtils.readRequestBodyAsString(request);
				StatusMsg msg = new StatusMsg(bodyContent,
						new java.util.Date(), user);
				msg = statusMsgService.save(msg);
				JSONObject json = new JSONObject();
				json.put("statusMsg", msg.toSimpleJson());
				json.put("ret", "share content success");
				retJson = json.toString();

			} catch (IOException e) {
				logger.error("read body content error: {}", e.getMessage());
			}

			logger.info("return json is", retJson);

			return new ResponseEntity<String>(retJson, HttpStatus.OK);
		} catch (OAuthProblemException e) {
			// 检查是否设置了错误码
			String errorCode = e.getError();
			if (OAuthUtils.isEmpty(errorCode)) {
				OAuthResponse oauthResponse = OAuthRSResponse
						.errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
						.setRealm(RESOURCE_SERVER_NAME).buildHeaderMessage();

				HttpHeaders headers = new HttpHeaders();
				headers.add(OAuth.HeaderType.WWW_AUTHENTICATE, oauthResponse
						.getHeader(OAuth.HeaderType.WWW_AUTHENTICATE));
				return new ResponseEntity<HttpHeaders>(headers,
						HttpStatus.UNAUTHORIZED);
			}

			OAuthResponse oauthResponse = OAuthRSResponse
					.errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
					.setRealm(RESOURCE_SERVER_NAME).setError(e.getError())
					.setErrorDescription(e.getDescription())
					.setErrorUri(e.getUri()).buildHeaderMessage();

			HttpHeaders headers = new HttpHeaders();
			headers.add(OAuth.HeaderType.WWW_AUTHENTICATE,
					oauthResponse.getHeader(OAuth.HeaderType.WWW_AUTHENTICATE));
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = { "/accessMsgs" }, method = RequestMethod.GET)
	public HttpEntity accessMsgs(HttpServletRequest request)
			throws OAuthSystemException {
		try {

			// 构建OAuth资源请求
			OAuthAccessResourceRequest oauthRequest = new OAuthAccessResourceRequest(
					request, ParameterStyle.QUERY);
			// 获取Access Token
			String accessToken = oauthRequest.getAccessToken();
			// request.

			// 验证Access Token
			if (!oAuthService.checkAccessToken(accessToken)) {
				// 如果不存在/过期了，返回未验证错误，需重新验证
				OAuthResponse oauthResponse = OAuthRSResponse
						.errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
						.setRealm(RESOURCE_SERVER_NAME)
						.setError(OAuthError.ResourceResponse.INVALID_TOKEN)
						.buildHeaderMessage();

				HttpHeaders headers = new HttpHeaders();
				headers.add(OAuth.HeaderType.WWW_AUTHENTICATE, oauthResponse
						.getHeader(OAuth.HeaderType.WWW_AUTHENTICATE));
				return new ResponseEntity<HttpHeaders>(headers,
						HttpStatus.UNAUTHORIZED);
			}
			// 返回用户名
			String username = oAuthService
					.getUsernameByAccessToken(accessToken);

			User user = userService.findByUsername(username);

			List list = statusMsgService.findAllVisible(user.getId());
			JSONArray array = EntityUtils.toJSONArray(list);
			String retJson = null;
			JSONObject json = new JSONObject();
			json.put("statusMsgList", array);
			json.put("ret", "get status messages success");
			retJson = json.toString();

			logger.info("return json is", retJson);

			return new ResponseEntity<String>(retJson, HttpStatus.OK);
		} catch (OAuthProblemException e) {
			// 检查是否设置了错误码
			String errorCode = e.getError();
			if (OAuthUtils.isEmpty(errorCode)) {
				OAuthResponse oauthResponse = OAuthRSResponse
						.errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
						.setRealm(RESOURCE_SERVER_NAME).buildHeaderMessage();

				HttpHeaders headers = new HttpHeaders();
				headers.add(OAuth.HeaderType.WWW_AUTHENTICATE, oauthResponse
						.getHeader(OAuth.HeaderType.WWW_AUTHENTICATE));
				return new ResponseEntity<HttpHeaders>(headers,
						HttpStatus.UNAUTHORIZED);
			}

			OAuthResponse oauthResponse = OAuthRSResponse
					.errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
					.setRealm(RESOURCE_SERVER_NAME).setError(e.getError())
					.setErrorDescription(e.getDescription())
					.setErrorUri(e.getUri()).buildHeaderMessage();

			HttpHeaders headers = new HttpHeaders();
			headers.add(OAuth.HeaderType.WWW_AUTHENTICATE,
					oauthResponse.getHeader(OAuth.HeaderType.WWW_AUTHENTICATE));
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
	}

}
