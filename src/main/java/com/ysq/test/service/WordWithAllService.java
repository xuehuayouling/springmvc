package com.ysq.test.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ysq.test.dao.ExampleDAO;
import com.ysq.test.dao.ExplainDAO;
import com.ysq.test.dao.PartOfSpeechDAO;
import com.ysq.test.dao.PronunciationDAO;
import com.ysq.test.dao.WordDAO;
import com.ysq.test.dao.WordExamDAO;
import com.ysq.test.dao.WordPosExplainDAO;
import com.ysq.test.dao.WordPronunciationDAO;
import com.ysq.test.dao.WpeExampleDAO;
import com.ysq.test.entity.Example;
import com.ysq.test.entity.Pronunciation;
import com.ysq.test.entity.Word;
import com.ysq.test.entity.WordExam;
import com.ysq.test.entity.WordPosExplain;
import com.ysq.test.entity.WordPronunciation;
import com.ysq.test.entity.WordWithAll;
import com.ysq.test.entity.WordWithAll.Meaning;
import com.ysq.test.entity.WpeExample;

@Service
@Transactional
public class WordWithAllService {
	@Autowired
	private WordDAO wordDAO;
	@Autowired
	private PartOfSpeechDAO partOfSpeechDAO;
	@Autowired
	private ExampleDAO exampleDAO;
	@Autowired
	private ExplainDAO explainDAO;
	@Autowired
	private WordPosExplainDAO wordPosExplainDAO;
	@Autowired
	private WpeExampleDAO wpeExampleDAO;
	@Autowired
	private WordPronunciationDAO wordPronunciationDAO;
	@Autowired
	private PronunciationDAO pronunciationDAO;
	@Autowired
	private WordExamDAO wordExamDAO;
	
	private void saveWordWithAll(WordWithAll wordWithAll) {
		long wordID = wordDAO.getIdByName(wordWithAll.getWord().getName());
		try {
			WordExam wordExam = new WordExam();
			wordExam.setExamID(1);
			wordExam.setWordID(wordID);
			wordExamDAO.save(wordExam);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (wordWithAll.getPronunciation() != null) {
			Pronunciation pronunciation = pronunciationDAO.queryBySpell(wordWithAll.getPronunciation().getSpell());
			long pronunciationID;
			if (pronunciation == null) {
				pronunciationID = pronunciationDAO.save(wordWithAll.getPronunciation());
			} else {
				pronunciationID = pronunciation.getId();
			}
			WordPronunciation wordPronunciation = new WordPronunciation();
			wordPronunciation.setWordID(wordID);
			wordPronunciation.setPronunciationID(pronunciationID);
			wordPronunciationDAO.save(wordPronunciation);
		}
		for (WordWithAll.Meaning meaning : wordWithAll.getMeanings()) {
			long partOfSpeechId = partOfSpeechDAO.getIdByName(meaning.getPartOfSpeech().getName());
			long explainId = explainDAO.getIdByContent(meaning.getExplain().getContent());
				WordPosExplain wordPosExplain = new WordPosExplain();
				wordPosExplain.setWordID(wordID);
				wordPosExplain.setPartOfSpeechID(partOfSpeechId);
				wordPosExplain.setExplainID(explainId);
				long wordPosExplainID = wordPosExplainDAO.save(wordPosExplain);
				for (Example example : meaning.getExamples()) {
					long exampleID = exampleDAO.getIdByContent(example.getContent());
					WpeExample wpeExample = new WpeExample();
					wpeExample.setExampleID(exampleID);
					wpeExample.setWpeID(wordPosExplainID);
					wpeExampleDAO.save(wpeExample);
				}
		}
	}

	public void saveWordWithAll(List<WordWithAll> wordWithAlls) {
		for (WordWithAll wordWithAll : wordWithAlls) {
			saveWordWithAll(wordWithAll);
		}
	}

	public Object saveTest() {
		return null;
	}

	public List<WordWithAll> getWordWithAllsByWordName(String name) {
		List<WordWithAll> wordWithAlls = new ArrayList<>();
		WordWithAll wordWithAll = new WordWithAll();
		Word word = wordDAO.queryByName(name);
		if (word == null) {
			return null;
		}
		wordWithAll.setWord(word);
		List<Meaning> meanings = new ArrayList<>();
		List<WordPosExplain> wordPosExplains = wordPosExplainDAO.queryByWordID(word.getId());
		for (WordPosExplain wordPosExplain : wordPosExplains) {
			Meaning meaning = wordWithAll.new Meaning();
			meaning.setExplain(explainDAO.queryByID(wordPosExplain.getExplainID()));
			meaning.setPartOfSpeech(partOfSpeechDAO.queryByID(wordPosExplain.getExplainID()));
			List<WpeExample> wpeExamples = wpeExampleDAO.queryByWpeID(wordPosExplain.getId());
			List<Example> examples = new ArrayList<>();
			for (WpeExample wpeExample : wpeExamples) {
				Example example = exampleDAO.queryByID(wpeExample.getExampleID());
				examples.add(example);
			}
			meaning.setExamples(examples);
			meanings.add(meaning);
		}
		wordWithAll.setMeanings(meanings );
		wordWithAlls.add(wordWithAll);
		return wordWithAlls;
	}
}
