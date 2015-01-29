package com.tenchael.toauth2.provider.domian;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tenchael.toauth2.provider.commons.EntityUtils;
import com.tenchael.toauth2.provider.commons.Jsonable;

@Entity(name = "statusMsg")
@Table(name = "t_status_msg")
public class StatusMsg implements Serializable, Jsonable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue
	private Long id;

	@Column(name = "content")
	private String content;

	@Column(name = "createTime")
	private Date createTime;

	@Column(name = "visible")
	private Boolean visible = true;

	@ManyToOne
	private User user;

	public StatusMsg() {
		super();
	}

	public StatusMsg(String content, Date createTime, User user) {
		super();
		this.content = content;
		this.createTime = createTime;
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;

		StatusMsg statusMsg = (StatusMsg) obj;

		if (id != null ? !id.equals(statusMsg.id) : statusMsg.id != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}

	@Override
	public String toString() {
		String[] attrs = { "id", "content", "visible", "createTime" };
		return EntityUtils.toString(this, attrs);
	}

	public JSONObject toSimpleJson() {
		String[] attrs = { "id", "content", "createTime" };
		JSONObject json = EntityUtils.toJsonObject(this, attrs);
		json.put("user", user.getUserDetails().getNickName());
		return json;
	}

}
