package com.ysq.test.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.ysq.test.dao.ExampleDAO;
import com.ysq.test.dao.ExplainDAO;
import com.ysq.test.dao.PartOfSpeechDAO;
import com.ysq.test.dao.RelationshipDAO;
import com.ysq.test.dao.WordDAO;
import com.ysq.test.entity.Example;
import com.ysq.test.entity.Explain;
import com.ysq.test.entity.PartOfSpeech;
import com.ysq.test.entity.Relationship;
import com.ysq.test.entity.Word;
import com.ysq.test.entity.WordWithAll;

@Service
@Transactional
public class WordSaverService {
	@Autowired
	private WordDAO wordDAO;
	@Autowired
	private PartOfSpeechDAO partOfSpeechDAO;
	@Autowired
	private ExampleDAO exampleDAO;
	@Autowired
	private ExplainDAO explainDAO;
	@Autowired
	private RelationshipDAO relationshipDAO;

	public WordDAO getWordDAO() {
		return wordDAO;
	}

	public void setWordDAO(WordDAO wordDAO) {
		this.wordDAO = wordDAO;
	}

	public PartOfSpeechDAO getPartOfSpeechDAO() {
		return partOfSpeechDAO;
	}

	public void setPartOfSpeechDAO(PartOfSpeechDAO partOfSpeechDAO) {
		this.partOfSpeechDAO = partOfSpeechDAO;
	}

	public ExampleDAO getExampleDAO() {
		return exampleDAO;
	}

	public void setExampleDAO(ExampleDAO exampleDAO) {
		this.exampleDAO = exampleDAO;
	}

	public ExplainDAO getExplainDAO() {
		return explainDAO;
	}

	public void setExplainDAO(ExplainDAO explainDAO) {
		this.explainDAO = explainDAO;
	}

	public RelationshipDAO getRelationshipDAO() {
		return relationshipDAO;
	}

	public void setRelationshipDAO(RelationshipDAO relationshipDAO) {
		this.relationshipDAO = relationshipDAO;
	}

	private boolean saveWordWithAll(WordWithAll wordWithAll) {
		Word word = wordDAO.addOrGetByName(wordWithAll.getWord().getName());
		for (WordWithAll.Meaning meaning : wordWithAll.getMeanings()) {
			PartOfSpeech partOfSpeech = partOfSpeechDAO.addOrGetByName(meaning.getPartOfSpeech().getName());
			Explain explain = explainDAO.addOrGetByContent(meaning.getExplain().getContent());
			for (Example example : meaning.getExamples()) {
				example = exampleDAO.addOrGetByContent(example.getContent());
				Relationship relationship = new Relationship();
				relationship.setWordID(word.getId());
				relationship.setPartOfSpeechID(partOfSpeech.getId());
				relationship.setExampleID(explain.getId());
				relationship.setExampleID(example.getId());
				relationshipDAO.saveRelationship(relationship);
			}
		}
		return false;
	}

	public void saveWordWithAll(List<WordWithAll> wordWithAlls) {
		try {
			for (WordWithAll wordWithAll : wordWithAlls) {
				saveWordWithAll(wordWithAll);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveTest() {
		String[] names = { "something", null };
		for (String name : names) {
			Word word = wordDAO.addOrGetByName(name);
		}
	}
}
