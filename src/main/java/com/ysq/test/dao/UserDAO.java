package com.ysq.test.dao;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.ysq.test.entity.User;
import com.ysq.test.util.SessionContext;
import com.ysq.test.util.TextUtil;

public class UserDAO {

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public User findUser(String name, String password) {
		if (TextUtil.isEmpty(name) || TextUtil.isEmpty(password)) {
			return null;
		}
		String sql = "from user where name=%s and password=%s";
		sql = String.format(sql, name, password);
		return queryUserBySql(sql);
	}
	
	public User addToken(User user, String sessionID) {
		if (user != null) {
			Session session = sessionFactory.getCurrentSession();
			user.setToken(sessionID);
			session.saveOrUpdate(user);
			session.flush();
			session.clear();
			return user;
		}
		return null;
	}

	public User findUserByAccessToken(String token) {
		if (TextUtil.isEmpty(token)) {
			return null;
		}
		String sql = "from user where token=%s";
		sql = String.format(sql, token);
		return queryUserBySql(sql);
	}
	
	private User queryUserBySql(String sql) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(sql);
		if (query.list().size() > 0) {
			User user = (User) query.list().get(0);
			return user;
		}
		return null;
	}
}
