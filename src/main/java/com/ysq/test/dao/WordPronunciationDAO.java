package com.ysq.test.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ysq.test.entity.WordPronunciation;

@Repository
public class WordPronunciationDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	public long save(WordPronunciation wordPronunciation) {
		Session session = sessionFactory.getCurrentSession();
		return (long) session.save(wordPronunciation);
	}

}
