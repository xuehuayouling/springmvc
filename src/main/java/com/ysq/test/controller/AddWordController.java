package com.ysq.test.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ysq.test.entity.Example;
import com.ysq.test.entity.Explain;
import com.ysq.test.entity.PartOfSpeech;
import com.ysq.test.entity.Word;
import com.ysq.test.entity.WordWithAll;
import com.ysq.test.service.WordWithAllService;
import com.ysq.test.util.HttpUtil;
import com.ysq.test.util.JsonUtil;
import com.ysq.test.util.TextUtil;

@Controller
@RequestMapping("/word")
public class AddWordController {
	private static final String BASE_URL = "http://www.collinsdictionary.com/dictionary/american-cobuild-learners/";
	private static final String URL_FOR_PHONETIC = "http://www.collinsdictionary.com/dictionary/american/";
	private static final String BASE_URL_FOR_VOICE = "http://dict.youdao.com/dictvoice?type=2&audio=";

	@Resource(name = "wordWithAllService")
	private WordWithAllService wordWithAllService;

	@RequestMapping(value = "/test")
	@ResponseBody
	public String tests(@RequestParam(value = "name", required = true) String name) {
		// List<WordWithAll> wordWithAlls =
		// wordWithAllService.getWordWithAllsByWordName(name);
		// String content = "success";
		// if (wordWithAlls == null) {
		// wordWithAlls = getWordWithAllsFormYoudao(name);
		// if (wordWithAlls == null) {
		// content = "analysis failure";
		// } else {
		// try {
		List<WordWithAll> wordWithAlls = getWordWithAllsFormYoudao(name);
		// wordWithAllService.saveWordWithAll(wordWithAlls);
		// } catch (Exception e) {
		// e.printStackTrace();
		// content = "Save failure";
		// }
		// }
		// }
		return JsonUtil.getJsonFromObject(wordWithAlls);
	}

	@RequestMapping(value = "/query")
	public ModelAndView query(@RequestParam(value = "name", required = true) String name) {
		ModelAndView mv = new ModelAndView();
		List<WordWithAll> wordWithAlls = wordWithAllService.getWordWithAllsByWordName(name);
		String content = "success";
		if (wordWithAlls == null) {
			wordWithAlls = addWord(name);
			if (wordWithAlls == null) {
				content = "add failure";
			}
		}
		if (wordWithAlls != null) {
			content = JsonUtil.getJsonFromObject(wordWithAlls);
		}
		mv.addObject("result", content);
		mv.setViewName("hello");
		return mv;
	}

	private List<WordWithAll> addWord(String name) {
		List<WordWithAll> wordWithAlls = getWordWithAllsFormYoudao(name);
		if (wordWithAlls != null) {
			try {
				wordWithAllService.saveWordWithAll(wordWithAlls);
				return wordWithAlls;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			Logger.getLogger(getClass()).debug("analyse failure");
		}
		return null;
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
				boolean hasSpecial = false;
				for (Element e1 : definition_content.children()) {
					if (e1.id() != null && e1.id().startsWith(name)) {
						Element definitions_hom_subsec_for_name = e1
								.getElementsByAttributeValueEnding("class", "orth h1_entry").first();
						String wordName = definitions_hom_subsec_for_name.html();
						int spanIndex = wordName.indexOf("<span");
						if (spanIndex > -1) {
							wordName = wordName.substring(0, spanIndex);
						}
						int supIndex = wordName.indexOf("<sup");
						if (supIndex > -1) {
							wordName = wordName.substring(0, supIndex);
						}
						if (!wordName.equals(name)) {
							continue;
						}
						Element definitions_hom_subsec = e1.getElementsByAttributeValueEnding("class", "hom-subsec")
								.first();
						WordWithAll wordWithAll = new WordWithAll();
						Word word = new Word();
						word.setName(wordName);
						wordWithAll.setWord(word);
						// String phonetic = getPhoneticByName(wordName, name);
						// Pronunciation pronunciation =
						// pronunciationService.queryBySpell(phonetic);
						// if (pronunciation != null) {
						// wordWithAll.setPronunciation(pronunciation);
						// } else {
						// if (HttpUtil.saveMp3ToDisk(BASE_URL_FOR_VOICE,
						// wordName)) {
						// pronunciation = new Pronunciation();
						// pronunciation.setVoicePath(wordName + ".mp3");
						// pronunciation.setSpell(phonetic);
						// pronunciationService.save(pronunciation);
						// wordWithAll.setPronunciation(pronunciation);
						// }
						// }
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
								if (!TextUtil.isEmpty(partOfSpeech.getName())
										&& !TextUtil.isEmpty(explain.getContent())) {
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

					} else if (e1.className().endsWith("gramGrp h3_entry")) {
						String partOfSpeechName = e1.getElementsByTag("span").get(0).text();
						if (partOfSpeechName.contains(".") && partOfSpeechName.indexOf(".") < 3) {
							partOfSpeechName = partOfSpeechName.substring(partOfSpeechName.indexOf(".") + 2);
							hasSpecial = true;
						}
					}
				}
			}
			return wordWithAlls;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private String getPhoneticByName(String name, String originName) {
		try {
			Document document = Jsoup.parse(HttpUtil.getURLContent(URL_FOR_PHONETIC + originName));
			Elements elements = document.getElementsByTag("body");
			Elements definition_main = elements.get(0).getElementsByClass("definition_main");
			if (!definition_main.isEmpty()) {
				Element definition_content = definition_main.first()
						.getElementsByAttributeValueContaining("class", "definition_content").first();
				for (Element e1 : definition_content.children()) {
					if (e1.id() != null && e1.id().startsWith(originName)) {
						Element definitions_hom_subsec_for_name = e1
								.getElementsByAttributeValueEnding("class", "orth h1_entry").first();
						String wordName = definitions_hom_subsec_for_name.html();
						wordName = wordName.substring(0, wordName.indexOf("<span"));
						if (name.equals(wordName)) {
							Element pron = definitions_hom_subsec_for_name
									.getElementsByAttributeValueContaining("class", "pron").first();
							String phonetic = pron.text();
							return phonetic.substring(1, phonetic.length() - 2);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static final String URL_YOUDAO_DICT = "http://dict.youdao.com/search?le=eng&keyfrom=dict.top&q=";

	private List<WordWithAll> getWordWithAllsFormYoudao(String name) {

		Document document = null;
		try {
			document = Jsoup.parse(HttpUtil.getURLContent(URL_YOUDAO_DICT + name));
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (document == null) {
			return null;
		}
		Element body = document.getElementsByTag("body").first();
		Elements errorWrappers = body.getElementsByAttributeValue("class", "error-wrapper");
		if (!errorWrappers.isEmpty()) {
			return null;
		}
		List<WordWithAll> wordWithAlls = new ArrayList<>();
		Elements keywords = body.getElementsByAttributeValueContaining("class", "keyword");
		if (keywords.isEmpty() || keywords.size() != 1) {
			Logger.getLogger(AddWordController.class).debug("The count of keyword is not right!");
		} else {
			WordWithAll wordWithAll = new WordWithAll();
			String wordName = keywords.first().text();
			Word word = new Word();
			word.setName(wordName);
			Elements pronounces = body.getElementsByAttributeValueContaining("class", "pronounce");
			if (pronounces.isEmpty() || pronounces.size() != 2) {
				Logger.getLogger(AddWordController.class).debug("The count of pronounce is not right!");
			} else {
				for (Element pronounce : pronounces) {
					Elements phonetics = pronounce.getElementsByAttributeValueContaining("class", "phonetic");
					if (phonetics.isEmpty() || phonetics.size() != 1) {
						Logger.getLogger(AddWordController.class).debug("The count of phonetic is not right!");
					} else {
						String phonetic = phonetics.first().text();
						phonetic = phonetic.substring(1, phonetic.length() - 1);
						Elements data_rels = pronounce.getElementsByTag("a");
						if (data_rels.isEmpty() || data_rels.size() != 1) {
							Logger.getLogger(AddWordController.class).debug("The count of data-rel is not right!");
						} else {
							String data_rel = data_rels.first().attr("data-rel").trim();
							String type = data_rel.substring(data_rel.length() - 1);
							if (Integer.valueOf(type) == 1) {
								word.setPronunciationE(phonetic);
							} else if (Integer.valueOf(type) == 2) {
								word.setPronunciationU(phonetic);
							}
						}
					}
				}
				wordWithAll.setWord(word);
			}
			Elements collinsToggles = body.getElementsByAttributeValue("class", "collinsToggle trans-container");
			if (collinsToggles.isEmpty() || collinsToggles.size() != 1) {
				Logger.getLogger(AddWordController.class).debug("The count of collinsToggles is not right!");
			} else {
				Elements lis = collinsToggles.first().getElementsByTag("li");
				if (lis.isEmpty() || lis.size() < 1) {
					Logger.getLogger(AddWordController.class).debug("The count of lis is not right!");
				} else {
					List<WordWithAll.Meaning> meanings = new ArrayList<>();
					for (Element li : lis) {
						WordWithAll.Meaning meaning = wordWithAll.new Meaning();
						Elements collinsMajorTrans = li.getElementsByAttributeValue("class", "collinsMajorTrans");
						if (collinsMajorTrans.isEmpty() || collinsMajorTrans.size() != 1) {
							Logger.getLogger(AddWordController.class)
									.debug("The count of collinsMajorTrans is not right!");
						} else {
							String p = collinsMajorTrans.first().getElementsByTag("p").text();
							Elements additionals = li.getElementsByAttributeValue("class", "additional");
							if (additionals.isEmpty() || additionals.size() < 1) {
								Logger.getLogger(AddWordController.class)
										.debug("The count of additional is not right!");
								continue;
							} else {
								String partOfSpeechName = additionals.first().text();
								PartOfSpeech partOfSpeech = new PartOfSpeech();
								partOfSpeech.setName(partOfSpeechName);
								Explain explain = new Explain();
								int chineseIndex = p.lastIndexOf(".");
								if (chineseIndex != -1) {
									explain.setContent(p.substring(partOfSpeechName.length() + 1, chineseIndex + 1));
									explain.setChineseMean(p.substring(chineseIndex + 2));
								} else {
									explain.setContent(p.substring(partOfSpeechName.length() + 1));
								}
								meaning.setPartOfSpeech(partOfSpeech);
								meaning.setExplain(explain);
							}

						}
						Elements exampleLists = li.getElementsByAttributeValue("class", "exampleLists");
						List<Example> list = new ArrayList<>();
						for (Element example : exampleLists) {
							Elements examples = example.getElementsByAttributeValue("class", "examples");
							if (examples.isEmpty() || examples.size() != 1) {
								Logger.getLogger(AddWordController.class)
										.debug("The count of examples is not right!" + example.html());
							} else {
								if (examples.first().children().isEmpty() || examples.first().children().size() != 2) {
									Logger.getLogger(AddWordController.class)
											.debug("The count of examples's children is not right!" + examples.html());
								} else {
									Example example2 = new Example();
									example2.setContent(examples.first().children().first().text());
									example2.setChineseContent(examples.first().children().last().text());
									list.add(example2);
								}
							}
						}
						meaning.setExamples(list);
						meanings.add(meaning);
					}
					wordWithAll.setMeanings(meanings);
				}
			}
			wordWithAlls.add(wordWithAll);
		}
		return wordWithAlls;
	}
}
