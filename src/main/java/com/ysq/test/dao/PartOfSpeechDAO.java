package com.ysq.test.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ysq.test.entity.PartOfSpeech;
import com.ysq.test.util.TextUtil;

@Repository
public class PartOfSpeechDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public PartOfSpeech getOrAddByName(String name) {
		if (findPartOfSpeech(name) == null) {
			addPartOfSpeech(name);
		}
		return findPartOfSpeech(name);
	}

	public void addPartOfSpeech(String name) {
		Session session = sessionFactory.getCurrentSession();
		PartOfSpeech PartOfSpeech = new PartOfSpeech();
		PartOfSpeech.setName(name);
		session.save(PartOfSpeech);
	}

	public PartOfSpeech findPartOfSpeech(String name) {
		if (TextUtil.isEmpty(name)) {
			return null;
		}
		String sql = "from t_part_of_speech where name='%s'";
		sql = String.format(sql, name);
		return queryPartOfSpeechBySql(sql);
	}

	private PartOfSpeech queryPartOfSpeechBySql(String sql) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(sql);
		if (query.list().size() > 0) {
			PartOfSpeech partOfSpeech = (PartOfSpeech) query.list().get(0);
			return partOfSpeech;
		}
		return null;
	}
}
