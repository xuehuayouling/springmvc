package com.ysq.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ysq.test.dao.WordDAO;
import com.ysq.test.entity.Word;

@Service
@Transactional
public class WordService {
	@Autowired
	private WordDAO wordDAO;

	public WordDAO getWordDAO() {
		return wordDAO;
	}

	public void setWordDAO(WordDAO wordDAO) {
		this.wordDAO = wordDAO;
	}

	public Word queryByName(String name) {
		return wordDAO.queryByName(name);
	}
}
