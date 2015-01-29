package com.tenchael.toauth2.provider.domian;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tenchael.toauth2.provider.commons.EntityUtils;
import com.tenchael.toauth2.provider.commons.Jsonable;

@Entity(name = "userDetails")
@Table(name = "t_user_details")
public class UserDetails implements Serializable, Jsonable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue
	private Long id;

	@Column(name = "gender")
	private String gender;

	@Column(name = "nickName")
	private String nickName;

	@Column(name = "email")
	private String email;

	@Column(name = "phone")
	private String phone;

	@Column(name = "description")
	private String description;

	@Column(name = "lastUpdate")
	private Date lastUpdate;

	@OneToOne
	private User user;

	public UserDetails() {
		super();
	}

	public UserDetails(User user, Date lastUpdate) {
		super();
		this.user = user;
		this.nickName = user.getUsername();
		this.lastUpdate = lastUpdate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}

		UserDetails userDetails = (UserDetails) obj;

		if (id != null ? !id.equals(userDetails.id) : userDetails.id != null) {
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
		String[] attrs = { "id", "gender", "nickName", "email", "phone" };
		return EntityUtils.toString(this, attrs);
	}

	public JSONObject toSimpleJson() {
		String[] attrs = { "id", "gender", "nickName", "email", "phone",
				"description", "lastUpdate" };
		JSONObject json = EntityUtils.toJsonObject(this, attrs);
		return json;
	}

}
