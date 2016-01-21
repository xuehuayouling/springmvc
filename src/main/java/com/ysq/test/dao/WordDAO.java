package com.ysq.test.dao;

import java.util.List;
import java.util.Random;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ysq.test.entity.Word;

@Repository
public class WordDAO {

	private static final String BASE_QUERY_SQL = "from Word as t_word where t_word.name=:name";
	private static final String BASE_QUERY_SQL_BY_ID = "from Word as t_word where t_word.id=:id";
	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * 通过名字获取实例，如果没有则返回null
	 * 
	 * @param name
	 * @return
	 */
	public Word queryByName(String name) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(BASE_QUERY_SQL);
		Word word = (Word) query.setString("name", name).uniqueResult();
		return word;
	}

	public Word queryByRandom() {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Word");
		List<Word> words = query.list();
		if (words.size() > 0) {
			Random random = new Random(words.size());
			return words.get(random.nextInt());
		}
		return null;
	}

	public long save(Word word) {
		Session session = sessionFactory.getCurrentSession();
		return (long) session.save(word);
	}
}
