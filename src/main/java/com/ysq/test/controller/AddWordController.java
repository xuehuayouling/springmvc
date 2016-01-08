package com.ysq.test.controller;

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
		String content = "";
		Word word = wordService.getWord(name);
		try {
			Document document = Jsoup.parse(HttpUtil.getURLContent(BASE_URL + name));
			Elements elements = document.getElementsByTag("body");
			Elements definition_main = elements.get(0).getElementsByClass("definition_main");
			Element definition_content =  definition_main.first().getElementsByAttributeValueContaining("class", "definition_content").first();
			for (Element e1 : definition_content.children()) {
				if (e1.id() != null && e1.id().startsWith(name)) {
					Element definitions_hom_subsec = e1.getElementsByAttributeValueEnding("class", "hom-subsec").first();
					for (Element e2 : definitions_hom_subsec.children()) {
						if (e2.id() != null && e2.id().startsWith(name)) {
							Relationship relationship = new Relationship();
							PartOfSpeech partOfSpeech = null;
							Example example = null;
							Explain explain = null;
							relationship.setWordID(word.getId());
							for (Element e3 : e2.children()) {
								if (e3.className().endsWith("gramGrp h3_entry")) {
									String partOfSpeechName = e3.getElementsByTag("span").get(0).text();
									partOfSpeech = partOfSpeechService.getPartOfSpeech(partOfSpeechName.substring(3));
								} else if (e3.className().endsWith("sense_list level_0")){
									for(Element e4 : e3.getElementsByTag("span")) {
										if (e4.className().equals("def")) {
											explain = explainService.getExplain(e4.text());
										} else if (e4.className().equals("orth")) {
											example = exampleService.getExample(e4.text().substring(4));
										}
									}
								}
							}
							relationship.setPartOfSpeechID(partOfSpeech.getId());
							relationship.setExampleID(example.getId());
							relationship.setExplainID(explain.getId());
							relationshipService.saveRelationship(relationship);
						}
					}
				} else if (e1.id() != null && e1.id().equals("translations_box")) {
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		mv.addObject("message", content);
		mv.setViewName("hello");
		return mv;
	}

}
