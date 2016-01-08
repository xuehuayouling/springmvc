package com.ysq.test.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ysq.test.entity.Example;
import com.ysq.test.util.TextUtil;

@Repository
public class ExampleDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public Example getOrAddByContent(String content) {
		if (findExample(content) == null) {
			addExample(content);
		}
		return findExample(content);
	}
	
	public void addExample(String content) {
		Session session = sessionFactory.getCurrentSession();
		Example example = new Example();
		example.setContent(content);
		session.save(example);
	}
	public Example findExample(String content) {
		if (TextUtil.isEmpty(content)) {
			return null;
		}
		content = content.replaceAll("'", "''");
		String sql = "from t_example where content='%s'";
		sql = String.format(sql, content);
		return queryExampleBySql(sql);
	}
	
	private Example queryExampleBySql(String sql) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(sql);
		if (query.list().size() > 0) {
			Example example = (Example) query.list().get(0);
			return example;
		}
		return null;
	}
}
