package com.ysq.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ysq.test.dao.ExampleDAO;
import com.ysq.test.entity.Example;

@Service
public class ExampleService {
	@Autowired
	private ExampleDAO exampleDAO;

	public ExampleDAO getExampleDAO() {
		return exampleDAO;
	}

	public void setExampleDAO(ExampleDAO exampleDAO) {
		this.exampleDAO = exampleDAO;
	}
	
	public Example getExample(String content) {
		return exampleDAO.getOrAddByContent(content);
	}

}
