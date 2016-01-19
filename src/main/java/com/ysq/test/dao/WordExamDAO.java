package com.ysq.test.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ysq.test.entity.Example;
import com.ysq.test.entity.WordExam;

@Repository
public class WordExamDAO {
	private static final String BASE_QUERY_SQL_BY_WORD_AND_EXAM_IDS = "from WordExam as t_word_exam where t_word_exam.examID=:examID and t_word_exam.wordID=:wordID";
	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	public long save(WordExam wordExam) {
		Session session = sessionFactory.getCurrentSession();
		return (long) session.save(wordExam);
	}

	public WordExam query(int examID, long wordID) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(BASE_QUERY_SQL_BY_WORD_AND_EXAM_IDS);
		WordExam wordExam = (WordExam) query.setLong("wordID", wordID).setLong("examID", examID).uniqueResult();
		return wordExam;
	}

}
