package com.ysq.test.dao;

import com.ysq.test.entity.Role;
import com.ysq.test.entity.User;
import com.ysq.test.util.TextUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RoleDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public Role queryById(long id) {
		Session session = getCurrentSession();
		Role role = (Role) session.get(Role.class, id);
		return role;
	}

	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	public void add(Role role) {
		getCurrentSession().save(role);
	}
}
