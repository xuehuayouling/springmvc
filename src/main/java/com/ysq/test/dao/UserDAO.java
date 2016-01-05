package com.ysq.test.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.ysq.test.entity.User;

public class UserDAO {
	
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public List<User> getAllUser() {
		String hsql = "from user";
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(hsql);
		return query.list();
	}

	public boolean login(String name, String password) {
		String sql = "from user where name=%s and password=%s";
		sql = String.format(sql, name, password);
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(sql);
		return query.list().size() > 0;
	}
}
