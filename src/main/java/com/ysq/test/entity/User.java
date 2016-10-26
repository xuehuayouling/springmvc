package com.ysq.test.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "t_user")
public class User implements Serializable {

	@Id
	@Basic(optional = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private long id;
	@Column(name = "name", nullable = false)
	private String name;
	@Column(name = "password")
	private String password;
	@Column(name = "token")
	private String token;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
