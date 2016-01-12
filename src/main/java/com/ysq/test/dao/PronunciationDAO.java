package com.ysq.test.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ysq.test.entity.Pronunciation;

@Repository
public class PronunciationDAO {

	private static final String BASE_QUERY_SQL_BY_SPELL = "from Pronunciation as t_pronunciation where t_pronunciation.spell=:spell";
	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	public Pronunciation queryBySpell(String spell) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(BASE_QUERY_SQL_BY_SPELL);
		Pronunciation pronunciation = (Pronunciation) query.setString("spell", spell).uniqueResult();
		return pronunciation;
	}

	public long getIdBySpell(String spell) {
		Pronunciation pronunciation = queryBySpell(spell);
		if (pronunciation != null) {
			return pronunciation.getId();
		} else {
			return 0;
		}
	}

	public long save(Pronunciation pronunciation) {
		Session session = sessionFactory.getCurrentSession();
		return (long) session.save(pronunciation);
	}

}
