package com.ysq.test.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ysq.test.entity.Example;

@Repository
public class ExampleDAO {

	private static final String BASE_QUERY_SQL_BY_CONTENT = "from Example as t_example where t_example.content=:content";
	private static final String BASE_QUERY_SQL_BY_ID = "from Example as t_example where t_example.id=:id";
	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public Example queryByContent(String content) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(BASE_QUERY_SQL_BY_CONTENT);
		Example example = (Example) query.setString("content", content).uniqueResult();
		return example;
	}

	public Example queryByID(long exampleID) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(BASE_QUERY_SQL_BY_ID);
		Example example = (Example) query.setLong("id", exampleID).uniqueResult();
		return example;
	}

	public long save(Example example) {
		Session session = sessionFactory.getCurrentSession();
		return (long) session.save(example);
	}
}
