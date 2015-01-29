package com.tenchael.toauth2.client.service.impl;

import static com.tenchael.toauth2.client.commons.Settings.PROVIDER_USER_INFO_URL;

import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthBearerClientRequest;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthResourceResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tenchael.toauth2.client.service.OAuthService;
import com.tenchael.toauth2.client.service.UserInfoService;

@Service
public class UserInfoServiceImpl implements UserInfoService {

	@Autowired
	private OAuthService oAuthService;

	public String getUserInfo(String sessionKey) {
		String userInfo = null;
		try {
			OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
			// request user info
			OAuthClientRequest userInfoRequest = new OAuthBearerClientRequest(
					PROVIDER_USER_INFO_URL).setAccessToken(
					oAuthService.getAccessToken(sessionKey))
					.buildQueryMessage();

			OAuthResourceResponse resourceResponse = oAuthClient.resource(
					userInfoRequest, OAuth.HttpMethod.GET,
					OAuthResourceResponse.class);
			userInfo = resourceResponse.getBody();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userInfo;
	}

}
