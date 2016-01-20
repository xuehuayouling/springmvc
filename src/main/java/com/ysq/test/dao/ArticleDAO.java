package com.ysq.test.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ysq.test.entity.Article;

@Repository
public class ArticleDAO {

	private static final String BASE_QUERY_SQL = "from Article ORDER BY id DESC";
	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public long save(Article article) {
		Session session = sessionFactory.getCurrentSession();
		return (long) session.save(article);
	}

	public List<Article> queryList() {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(BASE_QUERY_SQL);
		return query.list();
	}
}
