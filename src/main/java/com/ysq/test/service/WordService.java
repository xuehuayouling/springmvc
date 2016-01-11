package com.ysq.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ysq.test.dao.WordDAO;
import com.ysq.test.entity.Word;

@Service
public class WordService {
	@Autowired
	private WordDAO wordDAO;

	public WordDAO getWordDAO() {
		return wordDAO;
	}

	public void setWordDAO(WordDAO wordDAO) {
		this.wordDAO = wordDAO;
	}

	public Word getWord(Word word) {
		return wordDAO.addOrGetByName(word.getName());
	}

}
