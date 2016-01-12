package com.ysq.test.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ysq.test.entity.PartOfSpeech;

@Repository
public class PartOfSpeechDAO {

	private static final String BASE_QUERY_SQL_BY_NAME = "from PartOfSpeech as t_part_of_speech where t_part_of_speech.name=:name";
	private static final String BASE_QUERY_SQL_BY_ID = "from PartOfSpeech as t_part_of_speech where t_part_of_speech.id=:id";
	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public long getIdByName(String name) {
		PartOfSpeech partOfSpeech = queryByName(name);
		if (partOfSpeech != null) {
			return partOfSpeech.getId();
		} else {
			return addByName(name);
		}
	}

	private long addByName(String name) {
		Session session = sessionFactory.getCurrentSession();
		PartOfSpeech partOfSpeech = new PartOfSpeech();
		partOfSpeech.setName(name);
		return (long) session.save(partOfSpeech);
	}

	private PartOfSpeech queryByName(String name) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(BASE_QUERY_SQL_BY_NAME);
		PartOfSpeech partOfSpeech = (PartOfSpeech) query.setString("name", name).uniqueResult();
		return partOfSpeech;
	}

	public PartOfSpeech queryByID(long id) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(BASE_QUERY_SQL_BY_ID);
		PartOfSpeech partOfSpeech = (PartOfSpeech) query.setLong("id", id).uniqueResult();
		return partOfSpeech;
	}
}
