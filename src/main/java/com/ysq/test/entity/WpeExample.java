package com.ysq.test.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tr_wpe_example")
public class WpeExample {

	private long id;
	private long wpeID;
	private long exampleID;

	@Id
	@Basic(optional = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "wpe_id", nullable = false)
	public long getWpeID() {
		return wpeID;
	}

	public void setWpeID(long wpeID) {
		this.wpeID = wpeID;
	}

	@Column(name = "example_id", nullable = false)
	public long getExampleID() {
		return exampleID;
	}

	public void setExampleID(long exampleID) {
		this.exampleID = exampleID;
	}

}
