package com.ysq.test.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_word")
public class Word {

	private long id;
	private String name;
	private String pronunciationE;
	private String pronunciationU;
	private String pronunciationVoicePathE;
	private String pronunciationVoicePathU;

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
	
	@Column(name = "name", nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "pronunciation_e")
	public String getPronunciationE() {
		return pronunciationE;
	}

	public void setPronunciationE(String pronunciationE) {
		this.pronunciationE = pronunciationE;
	}

	@Column(name = "pronunciation_u")
	public String getPronunciationU() {
		return pronunciationU;
	}

	public void setPronunciationU(String pronunciationU) {
		this.pronunciationU = pronunciationU;
	}

	@Column(name = "pronunciation_voice_path_e")
	public String getPronunciationVoicePathE() {
		return pronunciationVoicePathE;
	}

	public void setPronunciationVoicePathE(String pronunciationVoicePathE) {
		this.pronunciationVoicePathE = pronunciationVoicePathE;
	}

	@Column(name = "pronunciation_voice_path_u")
	public String getPronunciationVoicePathU() {
		return pronunciationVoicePathU;
	}

	public void setPronunciationVoicePathU(String pronunciationVoicePathU) {
		this.pronunciationVoicePathU = pronunciationVoicePathU;
	}
	
}
