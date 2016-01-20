package com.ysq.test.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ysq.test.dao.ArticleDAO;
import com.ysq.test.entity.Article;

@Service
@Transactional
public class ArticleService {
	@Autowired
	private ArticleDAO articleDAO;

	public ArticleDAO getArticleDAO() {
		return articleDAO;
	}

	public void setArticleDAO(ArticleDAO articleDAO) {
		this.articleDAO = articleDAO;
	}

	public long save(Article article) {
		return articleDAO.save(article);
	}
	
	public List<Article> queryList() {
		return articleDAO.queryList();
	}
}
