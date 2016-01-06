package com.ysq.test.dao;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.ysq.test.entity.User;
import com.ysq.test.util.ValidVerifyUtil;

public class UserDAO {

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public User findUser(String name, String password) {
		String sql = "from user where name=%s and password=%s";
		sql = String.format(sql, name, password);
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(sql);
		if (query.list().size() > 0) {
			User user = (User) query.list().get(0);
			return user;
		}
		return null;
	}

	public User addToken(User user) {
		if (user != null) {
			Session session = sessionFactory.getCurrentSession();
			String token = String.valueOf(ValidVerifyUtil.getSessionID(user.getName()));
			user.setToken(token);
			session.saveOrUpdate(user);
			session.flush();
			session.clear();
			return user;
		}
		return null;
	}
}
