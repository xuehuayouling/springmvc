package com.ysq.test.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_word_pronunciation")
public class WordPronunciation {

	private long id;
	private long wordID;
	private long pronunciationID;

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

	@Column(name = "word_id", nullable = false)
	public long getWordID() {
		return wordID;
	}

	public void setWordID(long wordID) {
		this.wordID = wordID;
	}
	@Column(name = "pronunciation_id", nullable = false)
	public long getPronunciationID() {
		return pronunciationID;
	}

	public void setPronunciationID(long pronunciationID) {
		this.pronunciationID = pronunciationID;
	}

}
