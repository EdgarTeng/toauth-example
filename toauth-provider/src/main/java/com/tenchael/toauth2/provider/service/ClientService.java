package com.tenchael.toauth2.provider.service;

import com.tenchael.toauth2.provider.domian.Client;

public interface ClientService extends BaseService<Client, Long> {

	Client findByClientId(String clientId);

	Client findByClientSecret(String clientSecret);

}
