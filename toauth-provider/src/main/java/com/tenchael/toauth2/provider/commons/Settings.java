package com.tenchael.toauth2.provider.commons;

import java.util.Properties;

public class Settings {

	public static String MODE;

	private Properties configProperties;

	public String getProperty(final String property) throws NotFoundException {
		String retVal = configProperties.getProperty(property);
		if (retVal == null) {
			throw new NotFoundException("Property not found: " + property);
		}
		return retVal;
	}

	public Properties getConfigProperties() {
		return configProperties;
	}

	public void setConfigProperties(final Properties configProperties) {
		this.configProperties = configProperties;

		try {

			Settings.MODE = this
					.getProperty("com.tenchael.toauth2.provider.mode");

		} catch (NotFoundException e) {
			e.printStackTrace();
		}

	}
}
