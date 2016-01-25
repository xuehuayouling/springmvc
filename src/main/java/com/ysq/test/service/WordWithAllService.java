package com.ysq.test.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ysq.test.dao.ExampleDAO;
import com.ysq.test.dao.ExplainDAO;
import com.ysq.test.dao.PartOfSpeechDAO;
import com.ysq.test.dao.WordDAO;
import com.ysq.test.dao.WordExamDAO;
import com.ysq.test.dao.WordPosExplainDAO;
import com.ysq.test.dao.WpeExampleDAO;
import com.ysq.test.entity.Example;
import com.ysq.test.entity.Explain;
import com.ysq.test.entity.Word;
import com.ysq.test.entity.WordExam;
import com.ysq.test.entity.WordForShow;
import com.ysq.test.entity.WordPosExplain;
import com.ysq.test.entity.WordWithAll;
import com.ysq.test.entity.WordWithAll.Meaning;
import com.ysq.test.entity.WpeExample;
import com.ysq.test.util.HttpUtil;
import com.ysq.test.util.TextUtil;

@Service
@Transactional
public class WordWithAllService {
	private static final String BASE_URL_FOR_VOICE = "http://dict.youdao.com/dictvoice?type=%d&audio=%s";

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
	private WordExamDAO wordExamDAO;

	private void saveWordWithAll(WordWithAll wordWithAll) throws Exception {
		Word word = wordDAO.queryByName(wordWithAll.getWord().getName());
		long wordID = -1;
		if (word == null) {
			word = wordWithAll.getWord();
			if (word.getPronunciationE() != null) {
				HttpUtil.saveMp3ToDisk(String.format(BASE_URL_FOR_VOICE, 1, word.getName()), word.getName() + "_1.mp3");
				word.setPronunciationVoicePathE(word.getName() + "_1.mp3");
			}
			if (word.getPronunciationU() != null) {
				HttpUtil.saveMp3ToDisk(String.format(BASE_URL_FOR_VOICE, 2, word.getName()), word.getName() + "_2.mp3");
				word.setPronunciationVoicePathU(word.getName() + "_2.mp3");
			}
			wordID = wordDAO.save(word);
		} else {
			wordID = word.getId();
		}
		if (wordID < 0) {
			throw new Exception();
		}
		try {
			WordExam wordExam = new WordExam();
			wordExam.setExamID(1);
			wordExam.setWordID(wordID);
			if (wordExamDAO.query(1, wordID) == null) {
				wordExamDAO.save(wordExam);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (WordWithAll.Meaning meaning : wordWithAll.getMeanings()) {
			long partOfSpeechId = partOfSpeechDAO.getIdByName(meaning.getPartOfSpeech().getName());
			long explainId = -1;
			Explain explain = explainDAO.queryByContent(meaning.getExplain().getContent());
			if (explain != null) {
				explainId = explain.getId();
			} else {
				explainId = explainDAO.save(meaning.getExplain());
			}
			WordPosExplain wordPosExplain = new WordPosExplain();
			wordPosExplain.setWordID(wordID);
			wordPosExplain.setPartOfSpeechID(partOfSpeechId);
			wordPosExplain.setExplainID(explainId);
			long wordPosExplainID = wordPosExplainDAO.save(wordPosExplain);
			for (Example example : meaning.getExamples()) {
				long exampleID = -1;
				Example exampleExist = exampleDAO.queryByContent(example.getContent());
				if (exampleExist == null) {
					exampleID = exampleDAO.save(example);
				} else {
					exampleID = exampleExist.getId();
				}
				if (exampleID < 0) {
					throw new Exception();
				}
				WpeExample wpeExample = new WpeExample();
				wpeExample.setExampleID(exampleID);
				wpeExample.setWpeID(wordPosExplainID);
				wpeExampleDAO.save(wpeExample);
			}
		}
	}

	public void saveWordWithAll(List<WordWithAll> wordWithAlls) throws Exception {
		for (WordWithAll wordWithAll : wordWithAlls) {
			saveWordWithAll(wordWithAll);
		}
	}

	public Object saveTest() {
		return null;
	}

	public List<WordWithAll> getWordWithAllsByWordName(String name) {
		Word word = wordDAO.queryByName(name);
		if (word == null) {
			return null;
		}
		return getWordWithAllsByWord(word);
	}

	private List<WordWithAll> getWordWithAllsByWord(Word word) {
		if (word == null) {
			return null;
		}
		List<WordWithAll> wordWithAlls = new ArrayList<>();
		WordWithAll wordWithAll = new WordWithAll();
		wordWithAll.setWord(word);
		List<Meaning> meanings = new ArrayList<>();
		List<WordPosExplain> wordPosExplains = wordPosExplainDAO.queryByWordID(word.getId());
		for (WordPosExplain wordPosExplain : wordPosExplains) {
			Meaning meaning = wordWithAll.new Meaning();
			meaning.setExplain(explainDAO.queryByID(wordPosExplain.getExplainID()));
			meaning.setPartOfSpeech(partOfSpeechDAO.queryByID(wordPosExplain.getPartOfSpeechID()));
			List<WpeExample> wpeExamples = wpeExampleDAO.queryByWpeID(wordPosExplain.getId());
			List<Example> examples = new ArrayList<>();
			for (WpeExample wpeExample : wpeExamples) {
				Example example = exampleDAO.queryByID(wpeExample.getExampleID());
				examples.add(example);
			}
			meaning.setExamples(examples);
			meanings.add(meaning);
		}
		wordWithAll.setMeanings(meanings);
		wordWithAlls.add(wordWithAll);
		return wordWithAlls;
	}

	public List<WordWithAll> getWordWithAllsByRandom() {
		return getWordWithAllsByWord(wordDAO.queryByRandom());
	}

	public WordForShow getWordForShow(WordWithAll wordWithAll) {
		WordForShow wordForShow = new WordForShow();
		StringBuffer title = new StringBuffer();
		String pronunciation = wordWithAll.getWord().getPronunciationE();
		String pronunciationVioce = wordWithAll.getWord().getPronunciationVoicePathE();
		if (TextUtil.isEmpty(pronunciation)) {
			pronunciation = wordWithAll.getWord().getPronunciationU();
			pronunciationVioce = wordWithAll.getWord().getPronunciationVoicePathU();
		}
		title.append("<p>").append("<span style=font-size:25px>").append(wordWithAll.getWord().getName())
				.append("</span>").append("\t").append("<a href=# onclick=voice_play()>").append("[").append(pronunciation)
				.append("]").append("</a>").append("</p>").append("<br>")
				.append("<audio id=pronunciation_voice src=../sounds/").append(pronunciationVioce).append("></audio>");
		wordForShow.setTitle(title.toString());
		StringBuffer content = new StringBuffer();
		for (WordWithAll.Meaning meaning : wordWithAll.getMeanings()) {
			content.append("<li>").append("<div  style=background-color:#EFF5F8;>").append("<p font-size:12px;>")
					.append(meaning.getPartOfSpeech().getName()).append("  ").append(meaning.getExplain().getContent())
					.append("</p>").append("</div>");
			int i = 0;
			if (meaning.getExamples().size() > 0) {
				for (Example example : meaning.getExamples()) {
					content.append("<div  style=margin-left:20px;>").append("<p font-size:12px;>")
							.append(++i).append(". ").append(example.getContent()).append("</p>").append("</div>");
				}
			}
			content.append("</li>");
		}
		wordForShow.setContent(content.toString());
		return wordForShow;
	}
}
