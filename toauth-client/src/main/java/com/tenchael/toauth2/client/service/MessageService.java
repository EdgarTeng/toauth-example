package com.tenchael.toauth2.client.service;

import java.util.List;

import com.tenchael.toauth2.client.domain.Message;

public interface MessageService {

	String publish(String sessionKey, Message msg);

	String delete(String sessionKey, Long id);

	List<Message> getMessages(String sessionKey);

}
