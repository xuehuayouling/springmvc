package com.ysq.test.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ysq.test.entity.Word;
import com.ysq.test.util.TextUtil;

@Repository
public class WordDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public Word getOrAddByName(String name) {
		if (findWord(name) == null) {
			addWord(name);
		}
		return findWord(name);
	}
	
	public void addWord(String name) {
		Session session = sessionFactory.getCurrentSession();
		Word word = new Word();
		word.setName(name);
		session.save(word);
	}
	public Word findWord(String name) {
		if (TextUtil.isEmpty(name)) {
			return null;
		}
		String sql = "from t_word where name='%s'";
		sql = String.format(sql, name);
		return queryWordBySql(sql);
	}
	
	private Word queryWordBySql(String sql) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(sql);
		if (query.list().size() > 0) {
			Word word = (Word) query.list().get(0);
			return word;
		}
		return null;
	}
}
