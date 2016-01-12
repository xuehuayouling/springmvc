package com.ysq.test.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ysq.test.entity.WpeExample;

@Repository
public class WpeExampleDAO {

	private static final String BASE_QUERY_SQL_BY_WPEID = "from WpeExample as t_wpe_example where t_wpe_example.wpeID=:wpeID";
	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	public long save(WpeExample wpeExample) {
		Session session = sessionFactory.getCurrentSession();
		return (long) session.save(wpeExample);
	}

	public List<WpeExample> queryByWpeID(long wpeID) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(BASE_QUERY_SQL_BY_WPEID);
		List<WpeExample> wpeExamples = (List<WpeExample>) query.setLong("wpeID", wpeID).list();
		return wpeExamples;
	}

}
