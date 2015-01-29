package com.tenchael.toauth2.client.service;

public interface OAuthService {

	// 添加 auth code
	void addAuthCode(String sessionKey, String authCode);

	void addAccessToken(String sessionKey, String accessToken);

	void addRefreshToken(String sessionKey, String refreshToken);

	// 验证auth code是否有效
	boolean checkAuthCode(String sessionKey);

	// 验证access token是否有效
	boolean checkAccessToken(String sessionKey);

	// 验证refresh token是否有效
	boolean checkRefreshToken(String sessionKey);

	String getAuthCode(String sessionKey);

	String getAccessToken(String sessionKey);

	String getRefreshToken(String sessionKey);
	
	boolean authorize(String sessionKey);
}
