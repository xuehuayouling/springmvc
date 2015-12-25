package com.ysq.test.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.ysq.test.entity.User;

public class UserDAO {
//	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public List<User> getAllUser(){
		String hsql="from VW_K_PERSONNEL";
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(hsql);
		
		return query.list();
	}
}
