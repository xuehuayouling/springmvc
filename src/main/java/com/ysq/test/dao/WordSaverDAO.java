package com.ysq.test.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ysq.test.entity.Example;
import com.ysq.test.entity.Explain;
import com.ysq.test.entity.PartOfSpeech;
import com.ysq.test.entity.Relationship;
import com.ysq.test.entity.Word;
import com.ysq.test.entity.WordWithAll;

@Repository
public class WordSaverDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

}
