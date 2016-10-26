package com.ysq.test.dao;

import com.ysq.test.entity.Role;
import com.ysq.test.entity.Team;
import com.ysq.test.entity.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TeamDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public Team queryById(long id) {
		Session session = getCurrentSession();
		Team team = (Team) session.get(Team.class, id);
		return team;
	}

	public List<Team> queryUserById(long teamID, boolean includeChildren) {
		Team team = queryById(teamID);
		String sql = " from Team t WHERE t.code=:code or t.parentCode LIKE :likecode";
		Query query = getCurrentSession().createQuery(sql).setParameter("code", team.getCode()).setParameter("likecode", team.getCode() + "%");
		return query.list();
	}
	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	public void add(Team team) {
		getCurrentSession().save(team);
	}
}
