package com.ysq.test.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tr_word_pos_explain")
public class WordPosExplain {

	private long id;
	private long explainID;
	private long wordID;
	private long partOfSpeechID;

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

	@Column(name = "part_of_speech_id", nullable = false)
	public long getPartOfSpeechID() {
		return partOfSpeechID;
	}

	public void setPartOfSpeechID(long partOfSpeechID) {
		this.partOfSpeechID = partOfSpeechID;
	}

	@Column(name = "explain_id", nullable = false)
	public long getExplainID() {
		return explainID;
	}

	public void setExplainID(long explainID) {
		this.explainID = explainID;
	}
}
