package com.ysq.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ysq.test.dao.PartOfSpeechDAO;
import com.ysq.test.entity.PartOfSpeech;

@Service
public class PartOfSpeechService {
	@Autowired
	private PartOfSpeechDAO partOfSpeechDAO;

	public PartOfSpeechDAO getPartOfSpeechDAO() {
		return partOfSpeechDAO;
	}

	public void setPartOfSpeechDAO(PartOfSpeechDAO partOfSpeechDAO) {
		this.partOfSpeechDAO = partOfSpeechDAO;
	}
	
	public PartOfSpeech getPartOfSpeech(String name) {
		return partOfSpeechDAO.getOrAddByName(name);
	}

}
