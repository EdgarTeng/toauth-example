package com.tenchael.toauth2.provider.domian;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tenchael.toauth2.provider.commons.EntityUtils;
import com.tenchael.toauth2.provider.commons.Jsonable;

@Entity(name = "client")
@Table(name = "t_client")
public class Client implements Serializable, Jsonable {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "id")
	@GeneratedValue
	private Long id;

	@Column(name = "clientName")
	private String clientName;

	@Column(name = "clientId", unique = true)
	private String clientId;

	@Column(name = "clientSecret")
	private String clientSecret;

	@Column(name = "createTime")
	private Date createTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}

		Client client = (Client) obj;

		if (id != null ? !id.equals(client.id) : client.id != null) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}

	@Override
	public String toString() {
		String[] attrs = { "id", "clientName", "clientId", "clientSecret" };
		return EntityUtils.toString(this, attrs);
	}

	public JSONObject toSimpleJson() {
		String[] attrs = { "id", "clientName", "createTime" };
		JSONObject json = EntityUtils.toJsonObject(this, attrs);
		return json;
	}
}
