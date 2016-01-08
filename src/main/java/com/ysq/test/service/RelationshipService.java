package com.ysq.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ysq.test.dao.RelationshipDAO;
import com.ysq.test.entity.Relationship;

@Service
public class RelationshipService {
	@Autowired
	private RelationshipDAO explainDAO;

	public RelationshipDAO getExplainDAO() {
		return explainDAO;
	}

	public void setExplainDAO(RelationshipDAO explainDAO) {
		this.explainDAO = explainDAO;
	}
	
	public void saveRelationship(Relationship relationship) {
		explainDAO.saveRelationship(relationship);
	}
	
}
