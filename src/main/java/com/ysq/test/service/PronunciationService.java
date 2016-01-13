package com.ysq.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ysq.test.dao.PronunciationDAO;
import com.ysq.test.entity.Pronunciation;

@Service
@Transactional
public class PronunciationService {
	@Autowired
	private PronunciationDAO pronunciationDAO;

	public PronunciationDAO getPronunciationDAO() {
		return pronunciationDAO;
	}

	public void setPronunciationDAO(PronunciationDAO pronunciationDAO) {
		this.pronunciationDAO = pronunciationDAO;
	}
	
	public Pronunciation queryBySpell(String spell) {
		return pronunciationDAO.queryBySpell(spell);
	}
	
	public long save(Pronunciation pronunciation) {
		return pronunciationDAO.save(pronunciation);
	}
}
