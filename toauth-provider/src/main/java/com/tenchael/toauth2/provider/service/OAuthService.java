package com.tenchael.toauth2.provider.service;

public interface OAuthService {

	// 添加 auth code
	void addAuthCode(String authCode, String username);

	// 添加 access token
	public void addAccessToken(String accessToken, String username);

	// 添加 refresh token
	public void addRefreshToken(String refreshToken, String username);

	// 验证auth code是否有效
	boolean checkAuthCode(String authCode);

	// 验证access token是否有效
	boolean checkAccessToken(String accessToken);

	// 验证refresh token是否有效
	boolean checkRefreshToken(String refreshToken);

	String getUsernameByAuthCode(String authCode);

	String getUsernameByAccessToken(String accessToken);

	String getUsernameByRefreshToken(String refreshToken);

	// auth code / access token 过期时间
	long getExpireIn();

	boolean checkClientId(String clientId);

	boolean checkClientSecret(String clientSecret);

}
