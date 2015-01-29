package com.tenchael.toauth2.client.service.impl;

import static com.tenchael.toauth2.client.commons.Settings.PROVIDER_ACCESS_MSGS_URL;
import static com.tenchael.toauth2.client.commons.Settings.PROVIDER_SYNC_MESSAGE;

import java.util.ArrayList;
import java.util.List;

import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthBearerClientRequest;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthResourceResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tenchael.toauth2.client.domain.Message;
import com.tenchael.toauth2.client.service.MessageService;
import com.tenchael.toauth2.client.service.OAuthService;

@Service
public class MessageServiceImpl implements MessageService {

	@Autowired
	private OAuthService oAuthService;

	public String publish(String sessionKey, Message msg) {
		String ret = null;
		try {
			OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
			String accessToken = oAuthService.getAccessToken(sessionKey);
			// request user info
			OAuthClientRequest syncMsgRequest = new OAuthBearerClientRequest(
					PROVIDER_SYNC_MESSAGE).setAccessToken(accessToken)
					.buildQueryMessage();
			syncMsgRequest.setBody(msg.getContent());

			OAuthResourceResponse resourceResponse = oAuthClient.resource(
					syncMsgRequest, OAuth.HttpMethod.POST,
					OAuthResourceResponse.class);
			ret = resourceResponse.getBody();
		} catch (Exception e) {
			e.printStackTrace();
			ret = "failed";
		}
		return ret;
	}

	public String delete(String sessionKey, Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Message> getMessages(String sessionKey) {
		List<Message> list = new ArrayList<Message>();
		try {
			OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
			String accessToken = oAuthService.getAccessToken(sessionKey);
			// request user info
			OAuthClientRequest syncMsgRequest = new OAuthBearerClientRequest(
					PROVIDER_ACCESS_MSGS_URL).setAccessToken(accessToken)
					.buildQueryMessage();
			OAuthResourceResponse resourceResponse = oAuthClient.resource(
					syncMsgRequest, OAuth.HttpMethod.GET,
					OAuthResourceResponse.class);
			String ret = resourceResponse.getBody();
			JSONObject json = new JSONObject(ret);
			JSONArray array = json.getJSONArray("statusMsgList");
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObj = array.getJSONObject(i);
				Message message = new Message();
				message.setId(jsonObj.getLong("id"));
				message.setContent(jsonObj.getString("content"));
				message.setCreateTime(jsonObj.getString("createTime"));
				message.setPublisher(jsonObj.getString("user"));
				list.add(message);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
