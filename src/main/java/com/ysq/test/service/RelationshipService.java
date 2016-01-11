package com.ysq.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ysq.test.dao.RelationshipDAO;
import com.ysq.test.entity.Relationship;

@Service
public class RelationshipService {
	@Autowired
	private RelationshipDAO relationshipDAO;

	
	public RelationshipDAO getRelationshipDAO() {
		return relationshipDAO;
	}


	public void setRelationshipDAO(RelationshipDAO relationshipDAO) {
		this.relationshipDAO = relationshipDAO;
	}


	public void saveRelationship(Relationship relationship) {
		relationshipDAO.saveRelationship(relationship);
	}
	
}
