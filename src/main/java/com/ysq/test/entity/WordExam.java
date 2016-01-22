package com.ysq.test.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tr_word_exam")
public class WordExam {

	private long id;
	private long examID;
	private long wordID;

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

	@Column(name = "exam_id", nullable = false)
	public long getExamID() {
		return examID;
	}

	public void setExamID(long examID) {
		this.examID = examID;
	}

}
