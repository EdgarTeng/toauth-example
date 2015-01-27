package com.tenchael.toauth2.client.commons;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Settings {

	// application mode
	public static String MODE;

	// provider base url
	public static String PROVIDER_BASE_URL;
	// provider authorize url
	public static String PROVIDER_AUTHORIZE_URL;
	// provider obtain access_token url
	public static String PROVIDER_ACCESS_TOKEN_URL;
	// provider obtain user infomation url
	public static String PROVIDER_USER_INFO_URL;

	public static String CLIENT_BASE_URL;
	public static String CLIENT_REDIRECT_URL;

	public static String CLIENT_ID;
	public static String CLIENT_SECRET;

	private static Properties configProperties;

	public static String getProperty(final String property)
			throws NotFoundException {
		String retVal = configProperties.getProperty(property);
		if (retVal == null) {
			throw new NotFoundException("Property not found: " + property);
		}
		return retVal;
	}

	public static Properties getConfigProperties() {
		return configProperties;
	}

	public static String getProviderAuthorizeUrl() {
		return PROVIDER_AUTHORIZE_URL;
	}

	public static String getProviderAccessTokenUrl() {
		return PROVIDER_ACCESS_TOKEN_URL;
	}

	public static String getProviderUserInfoUrl() {
		return PROVIDER_USER_INFO_URL;
	}
	
	public static String getClientRedirectUrl(){
		return CLIENT_REDIRECT_URL;
	}

	public static void setConfigProperties(final Properties configProperties) {
		Settings.configProperties = configProperties;

		try {
			{
				MODE = getProperty("mode");
			}
			{
				// setting parameters for client
				CLIENT_BASE_URL = getProperty("client.base.url");
				CLIENT_REDIRECT_URL = CLIENT_BASE_URL
						+ getProperty("client.redirect");
				CLIENT_ID = getProperty("client.clientId");
				CLIENT_SECRET = getProperty("client.clientSecret");
			}
			{
				// setting parameters for provider
				PROVIDER_BASE_URL = getProperty("provider.base.url");
				PROVIDER_ACCESS_TOKEN_URL = PROVIDER_BASE_URL
						+ getProperty("provider.accessToken");
				PROVIDER_USER_INFO_URL = PROVIDER_BASE_URL
						+ getProperty("provider.userInfo");
				{
					// joint authorize url
					String authorizeBaseUrl = PROVIDER_BASE_URL + getProperty("provider.authorize");
					Map<String, String> params = new HashMap<String, String>();
					params.put("client_id", CLIENT_ID);
					params.put("response_type", "code");
					params.put("redirect_uri", CLIENT_REDIRECT_URL);
					PROVIDER_AUTHORIZE_URL = HttpUtils.jointParams(
							authorizeBaseUrl, params);
				}
			}

		} catch (NotFoundException e) {
			e.printStackTrace();
		}

	}
}
