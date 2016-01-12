package com.ysq.test.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ysq.test.entity.Explain;

@Repository
public class ExplainDAO {

	private static final String BASE_QUERY_SQL_BY_CONTENT = "from Explain as t_explain where t_explain.content=:content";
	private static final String BASE_QUERY_SQL_BY_ID = "from Explain as t_explain where t_explain.id=:id";
	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public long getIdByContent(String content) {
		Explain explain = queryByContent(content);
		if (explain != null) {
			return explain.getId();
		} else {
			return addByContent(content);
		}
	}

	private long addByContent(String content) {
		Session session = sessionFactory.getCurrentSession();
		Explain explain = new Explain();
		explain.setContent(content);
		return (long) session.save(explain);
	}

	private Explain queryByContent(String content) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(BASE_QUERY_SQL_BY_CONTENT);
		Explain explain = (Explain) query.setString("content", content).uniqueResult();
		return explain;
	}
	
	public Explain queryByID(long id) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(BASE_QUERY_SQL_BY_ID);
		Explain explain = (Explain) query.setLong("id", id).uniqueResult();
		return explain;
	}
}
