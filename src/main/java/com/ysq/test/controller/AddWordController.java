package com.ysq.test.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ysq.test.entity.Example;
import com.ysq.test.entity.Explain;
import com.ysq.test.entity.PartOfSpeech;
import com.ysq.test.entity.Word;
import com.ysq.test.entity.WordWithAll;
import com.ysq.test.service.WordSaverService;
import com.ysq.test.util.HttpUtil;
import com.ysq.test.util.JsonUtil;
import com.ysq.test.util.TextUtil;

@Controller
@RequestMapping("/addWord")
public class AddWordController {
	@Resource(name = "wordSaverService")
	private WordSaverService wordSaverService;
	private static final String BASE_URL = "http://www.collinsdictionary.com/dictionary/american-cobuild-learners/";

	@RequestMapping(value = "/test")
	public ModelAndView test() {
		ModelAndView mv = new ModelAndView();
		String content = "";
		try {
			wordSaverService.saveTest();
		} catch(Exception e) {
			e.printStackTrace();
		}
		mv.addObject("message", content);
		mv.setViewName("hello");
		return mv;
	}
	
	@RequestMapping(value = "/addWord")
	public ModelAndView count(@RequestParam(value = "name", required = true) String name) {
		ModelAndView mv = new ModelAndView();
		String content = "failure";
		List<WordWithAll> wordWithAlls = getWordWithAllsFormCollins(name);
		if (wordWithAlls == null) {
			content = "analysis failure";
		} else {
				try {
					wordSaverService.saveWordWithAll(wordWithAlls);
					content = JsonUtil.getJsonFromObject(wordWithAlls);
				} catch (Exception e) {
					e.printStackTrace();
					content = "Save failure";
				}
		}
		mv.addObject("message", content);
		mv.setViewName("hello");
		return mv;
	}

	private List<WordWithAll> getWordWithAllsFormCollins(String name) {
		try {
			List<WordWithAll> wordWithAlls = new ArrayList<>();
			Document document = Jsoup.parse(HttpUtil.getURLContent(BASE_URL + name));
			Elements elements = document.getElementsByTag("body");
			Elements definition_main = elements.get(0).getElementsByClass("definition_main");
			if (!definition_main.isEmpty()) {
				Element definition_content = definition_main.first()
						.getElementsByAttributeValueContaining("class", "definition_content").first();
				for (Element e1 : definition_content.children()) {
					if (e1.id() != null && e1.id().startsWith(name)) {
						Element definitions_hom_subsec_for_name = e1
								.getElementsByAttributeValueEnding("class", "orth h1_entry").first();
						String wordName = definitions_hom_subsec_for_name.html();
						wordName = wordName.substring(0, wordName.indexOf("<span>"));
						Element definitions_hom_subsec = e1.getElementsByAttributeValueEnding("class", "hom-subsec")
								.first();
						WordWithAll wordWithAll = new WordWithAll();
						Word word = new Word();
						word.setName(wordName);
						wordWithAll.setWord(word);
						List<WordWithAll.Meaning> meanings = new ArrayList<>();
						for (Element e2 : definitions_hom_subsec.children()) {
							if (e2.id() != null && e2.id().startsWith(name)) {
								WordWithAll.Meaning meaning = wordWithAll.new Meaning();
								PartOfSpeech partOfSpeech = new PartOfSpeech();
								List<Example> examples = new ArrayList<>();
								Explain explain = new Explain();
								for (Element e3 : e2.children()) {
									if (e3.className().endsWith("gramGrp h3_entry")) {
										String partOfSpeechName = e3.getElementsByTag("span").get(0).text();
										if (partOfSpeechName.contains(".") && partOfSpeechName.indexOf(".") < 3) {
											partOfSpeechName = partOfSpeechName
													.substring(partOfSpeechName.indexOf(".") + 2);
										}
										partOfSpeech.setName(partOfSpeechName);
									} else if (e3.className().endsWith("sense_list level_0")) {
										for (Element e4 : e3.getElementsByTag("span")) {
											if (e4.className().equals("def")) {
												explain.setContent(e4.text());
											} else if (e4.className().equals("orth")) {
												Example example = new Example();
												example.setContent(e4.text().substring(4));
												examples.add(example);
											}
										}
									}
								}
								if (!TextUtil.isEmpty(partOfSpeech.getName()) && !TextUtil.isEmpty(explain.getContent())) {
									meaning.setPartOfSpeech(partOfSpeech);
									meaning.setExamples(examples);
									meaning.setExplain(explain);
									meanings.add(meaning);
								}
							}
						}
						if (meanings.size() > 0) {
							wordWithAll.setMeanings(meanings);
							wordWithAlls.add(wordWithAll);
						}
					} else if (e1.id() != null && e1.id().equals("translations_box")) {

					}
				}
			}
			return wordWithAlls;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
