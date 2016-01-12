package com.ysq.test.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ysq.test.entity.Word;

@Repository
public class WordDAO {

	private static final String BASE_QUERY_SQL = "from Word as t_word where t_word.name=:name";
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
	 * @param name
	 * @return
	 */
	public Word queryByName(String name) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(BASE_QUERY_SQL);
		Word word = (Word) query.setString("name", name).uniqueResult();
		return word;
	}

	/**
	 * 通过名字获取id，如果数据库中没有，则添加新的并返回id。
	 * @param name
	 * @return id
	 */
	public long getIdByName(String name) {
		Word word = queryByName(name);
		if (word != null) {
			return word.getId();
		} else {
			return addByName(name);
		}
	}

	private long addByName(String name) {
		Session session = sessionFactory.getCurrentSession();
		Word word = new Word();
		word.setName(name);
		return (long) session.save(word);
	}
}
