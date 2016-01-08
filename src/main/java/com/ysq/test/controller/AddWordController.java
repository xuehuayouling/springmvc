package com.ysq.test.controller;

import javax.annotation.Resource;

import org.hibernate.exception.ConstraintViolationException;
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
import com.ysq.test.entity.Relationship;
import com.ysq.test.entity.Word;
import com.ysq.test.service.ExampleService;
import com.ysq.test.service.ExplainService;
import com.ysq.test.service.PartOfSpeechService;
import com.ysq.test.service.RelationshipService;
import com.ysq.test.service.WordService;
import com.ysq.test.util.HttpUtil;

@Controller
@RequestMapping("/addWord")
public class AddWordController {
	@Resource(name = "wordService")
	private WordService wordService;
	@Resource(name = "partOfSpeechService")
	private PartOfSpeechService partOfSpeechService;
	@Resource(name = "exampleService")
	private ExampleService exampleService;
	@Resource(name = "explainService")
	private ExplainService explainService;
	@Resource(name = "relationshipService")
	private RelationshipService relationshipService;
	private static final String BASE_URL = "http://www.collinsdictionary.com/dictionary/american-cobuild-learners/";

	@RequestMapping(value = "/addWord")
	public ModelAndView count(@RequestParam(value = "name", required = true) String name) {
		ModelAndView mv = new ModelAndView();
		String content = "failure";
		try {
			Document document = Jsoup.parse(HttpUtil.getURLContent(BASE_URL + name));
			Elements elements = document.getElementsByTag("body");
			Elements definition_main = elements.get(0).getElementsByClass("definition_main");
			if (!definition_main.isEmpty()) {
				Element definition_content = definition_main.first()
						.getElementsByAttributeValueContaining("class", "definition_content").first();
				for (Element e1 : definition_content.children()) {
					if (e1.id() != null && e1.id().startsWith(name)) {
						Element definitions_hom_subsec_for_name = e1.getElementsByAttributeValueEnding("class", "orth h1_entry")
								.first();
						String wordName = definitions_hom_subsec_for_name.html();
						wordName = wordName.substring(0, wordName.indexOf("<span>"));
						Element definitions_hom_subsec = e1.getElementsByAttributeValueEnding("class", "hom-subsec")
								.first();
						for (Element e2 : definitions_hom_subsec.children()) {
							if (e2.id() != null && e2.id().startsWith(name)) {
								try {
									Relationship relationship = new Relationship();
									PartOfSpeech partOfSpeech = null;
									Example example = null;
									Explain explain = null;
									for (Element e3 : e2.children()) {
										if (e3.className().endsWith("gramGrp h3_entry")) {
											String partOfSpeechName = e3.getElementsByTag("span").get(0).text();
											if (partOfSpeechName.contains(".") && partOfSpeechName.indexOf(".") < 3) {
												partOfSpeechName = partOfSpeechName
														.substring(partOfSpeechName.indexOf(".") + 2);
											}
											partOfSpeech = partOfSpeechService.getPartOfSpeech(partOfSpeechName);
										} else if (e3.className().endsWith("sense_list level_0")) {
											for (Element e4 : e3.getElementsByTag("span")) {
												if (e4.className().equals("def")) {
													explain = explainService.getExplain(e4.text());
												} else if (e4.className().equals("orth")) {
													example = exampleService.getExample(e4.text().substring(4));
												}
											}
										}
									}
									Word word = wordService.getWord(wordName);
									if (word != null && partOfSpeech != null && example != null && explain != null) {
										relationship.setWordID(word.getId());
										relationship.setPartOfSpeechID(partOfSpeech.getId());
										relationship.setExampleID(example.getId());
										relationship.setExplainID(explain.getId());
										relationshipService.saveRelationship(relationship);
									}
								} catch (ConstraintViolationException e) {
									e.printStackTrace();
								}
							}
						}
					} else if (e1.id() != null && e1.id().equals("translations_box")) {

					}
				}
				content = "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		mv.addObject("message", content);
		mv.setViewName("hello");
		return mv;
	}

}
