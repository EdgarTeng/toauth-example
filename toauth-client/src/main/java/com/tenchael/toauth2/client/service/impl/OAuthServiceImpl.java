package com.tenchael.toauth2.client.service.impl;

import static com.tenchael.toauth2.client.commons.Constants.ACCESS_TOKEN;
import static com.tenchael.toauth2.client.commons.Constants.AUTH_CODE;
import static com.tenchael.toauth2.client.commons.Constants.REFRESH_TOKEN;
import static com.tenchael.toauth2.client.commons.Settings.CLIENT_HOME_URL;
import static com.tenchael.toauth2.client.commons.Settings.CLIENT_ID;
import static com.tenchael.toauth2.client.commons.Settings.CLIENT_SECRET;
import static com.tenchael.toauth2.client.commons.Settings.PROVIDER_ACCESS_TOKEN_URL;

import java.util.HashMap;
import java.util.Map;

import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthAccessTokenResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import com.tenchael.toauth2.client.service.OAuthService;

@Service
public class OAuthServiceImpl implements OAuthService {

	private Cache cache;

	private static final Logger logger = LoggerFactory
			.getLogger(OAuthServiceImpl.class);

	@Autowired
	public OAuthServiceImpl(CacheManager cacheManager) {
		this.cache = cacheManager.getCache("code-cache");
	}

	public void addAuthCode(String sessionKey, String authCode) {
		String key = sessionKey + "." + AUTH_CODE;
		cache.put(key, authCode);

	}

	public void addAccessToken(String sessionKey, String accessToken) {
		String key = sessionKey + "." + ACCESS_TOKEN;
		cache.put(key, accessToken);
	}

	public void addRefreshToken(String sessionKey, String refreshToken) {
		String key = sessionKey + "." + REFRESH_TOKEN;
		cache.put(key, refreshToken);
	}

	public boolean checkAuthCode(String sessionKey) {
		String key = sessionKey + "." + AUTH_CODE;
		return cache.get(key) != null;
	}

	public boolean checkAccessToken(String sessionKey) {
		String key = sessionKey + "." + ACCESS_TOKEN;
		return cache.get(key) != null;
	}

	public boolean checkRefreshToken(String sessionKey) {
		String key = sessionKey + "." + REFRESH_TOKEN;
		return cache.get(key) != null;
	}

	public String getAuthCode(String sessionKey) {
		String key = sessionKey + "." + AUTH_CODE;
		return (String) cache.get(key).get();
	}

	public String getAccessToken(String sessionKey) {
		String key = sessionKey + "." + ACCESS_TOKEN;
		return (String) cache.get(key).get();
	}

	public String getRefreshToken(String sessionKey) {
		String key = sessionKey + "." + REFRESH_TOKEN;
		return (String) cache.get(key).get();
	}

	public boolean authorize(String sessionKey) {
		// auth code is null, refers to have no authorization
		if (!checkAuthCode(sessionKey)) {
			return false;
		}

		if (checkAccessToken(sessionKey) && checkRefreshToken(sessionKey)) {
			return true;
		}

		OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());

		String authCode = getAuthCode(sessionKey);
		Map<String, String> params = new HashMap<String, String>();
		params.put("sessionKey", sessionKey);
		// String redirectUrl = HttpUtils.jointParams(CLIENT_REDIRECT_URL,
		// params);

		try {
			// request access token
			OAuthClientRequest accessTokenRequest = OAuthClientRequest
					.tokenLocation(PROVIDER_ACCESS_TOKEN_URL)
					.setGrantType(GrantType.AUTHORIZATION_CODE)
					.setClientId(CLIENT_ID).setClientSecret(CLIENT_SECRET)
					.setRedirectURI(CLIENT_HOME_URL).setCode(authCode)
					.buildQueryMessage();

			OAuthAccessTokenResponse oAuthResponse = oAuthClient.accessToken(
					accessTokenRequest, OAuth.HttpMethod.POST);

			String accessToken = oAuthResponse.getAccessToken();
			String refreshToken = oAuthResponse.getRefreshToken();
			// Long expiresIn = oAuthResponse.getExpiresIn();
			addAccessToken(sessionKey, accessToken);
			addRefreshToken(sessionKey, refreshToken);
			logger.info("accessToken is: {}", accessToken);
			logger.info("refresh token is: {}", refreshToken);
		} catch (Exception e) {
			logger.error("request provider access token error: {}",
					e.getMessage());
			return false;
		}

		return true;

	}

}
