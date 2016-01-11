package com.ysq.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ysq.test.dao.ExplainDAO;
import com.ysq.test.entity.Explain;

@Service
public class ExplainService {
	@Autowired
	private ExplainDAO explainDAO;

	public ExplainDAO getExplainDAO() {
		return explainDAO;
	}

	public void setExplainDAO(ExplainDAO explainDAO) {
		this.explainDAO = explainDAO;
	}
	
	public Explain getExplain(Explain explain) {
		return explainDAO.addOrGetByContent(explain.getContent());
	}

}
