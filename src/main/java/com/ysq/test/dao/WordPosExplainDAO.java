package com.ysq.test.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ysq.test.entity.WordPosExplain;

@Repository
public class WordPosExplainDAO {

	private static final String BASE_QUERY_SQL = "from WordPosExplain as t_word_pos_explain where t_word_pos_explain.wordID=:wordID";
	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	public long save(WordPosExplain relationship) {
		Session session = sessionFactory.getCurrentSession();
		return (long) session.save(relationship);
	}

	/**
	 * 根据单词id查找出所有的Relationship，没有则返回null
	 * @param wordID
	 * @return
	 */
	public List<WordPosExplain> queryByWordID(long wordID) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(BASE_QUERY_SQL);
		List<WordPosExplain> relationships = (List<WordPosExplain>) query.setLong("wordID", wordID).list();
		return relationships;
	}
}
