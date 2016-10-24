package com.ysq.test.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ysq.test.entity.User;
import com.ysq.test.util.TextUtil;

@Repository
public class UserDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public User findUser(String name, String password) {
		if (TextUtil.isEmpty(name) || TextUtil.isEmpty(password)) {
			return null;
		}
		String sql = "from t_user where name='%s' and password='%s'";
		sql = String.format(sql, name, password);
		return queryUserBySql(sql);
	}
	
	public User addToken(User user, String sessionID) {
		if (user != null) {
			Session session = getCurrentSession();
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
		String sql = "from t_user where token='%s'";
		sql = String.format(sql, token);
		return queryUserBySql(sql);
	}
	
	private User queryUserBySql(String sql) {
		Session session = getCurrentSession();
		Query query = session.createQuery(sql);
		if (query.list().size() > 0) {
			User user = (User) query.list().get(0);
			return user;
		}
		return null;
	}

	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	public void add(User user) {
		getCurrentSession().save(user);
	}
}
