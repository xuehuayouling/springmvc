package com.ysq.test.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ysq.test.entity.Explain;
import com.ysq.test.util.TextUtil;

@Repository
public class ExplainDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public Explain getOrAddByContent(String content) {
		if (findExplain(content) == null) {
			addExplain(content);
		}
		return findExplain(content);
	}
	
	public void addExplain(String content) {
		Session session = sessionFactory.getCurrentSession();
		Explain explain = new Explain();
		explain.setContent(content);
		session.save(explain);
	}
	public Explain findExplain(String content) {
		if (TextUtil.isEmpty(content)) {
			return null;
		}
		content = content.replaceAll("'", "''");
		String sql = "from t_explain where content='%s'";
		sql = String.format(sql, content);
		return queryExplainBySql(sql);
	}
	
	private Explain queryExplainBySql(String sql) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(sql);
		if (query.list().size() > 0) {
			Explain explain = (Explain) query.list().get(0);
			return explain;
		}
		return null;
	}
}
