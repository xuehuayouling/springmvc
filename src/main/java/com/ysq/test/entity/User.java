package com.ysq.test.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "VW_K_PERSONNEL")
public class User {

	@Column(name = "zwxm00")
	private String zwxm00;
	@Column(name = "xb0000")
	private String xb0000;
	@Id
	@Column(name = "xkh000")
	private String xkh000;

	public String getZwxm00() {
		return zwxm00;
	}

	public void setZwxm00(String zwxm00) {
		this.zwxm00 = zwxm00;
	}

	public String getXb0000() {
		return xb0000;
	}

	public void setXb0000(String xb0000) {
		this.xb0000 = xb0000;
	}

	public String getXkh000() {
		return xkh000;
	}

	public void setXkh000(String xkh000) {
		this.xkh000 = xkh000;
	}
}
