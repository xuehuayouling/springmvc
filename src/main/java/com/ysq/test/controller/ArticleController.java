package com.ysq.test.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ysq.test.entity.Article;
import com.ysq.test.service.ArticleService;

@Controller
@RequestMapping("/article")
public class ArticleController {
	@Resource(name = "articleService")
	private ArticleService articleService;

	public static final String[][] FRIENDS_NAMES = {
			{" Joey:", "\nJoey:"},
			{" Monica:", "\nMonica:"},
			{" Phoebe:", "\nPhoebe:"},
			{" Chandler:", "\nChandler:"},
			{" Ross:", "\nRoss:"},
			{" Rachel:", "\nRachel:"},
			{" \\[Scene:", "\n\\[Scene:"}
			};
	public static final String[][] FRIENDS_NAMES_VIEW = {
			{"\nJoey:", "<br><span style=\"color:red;\">Joey:</span>"},
			{"\nMonica:", "<br><span style=\"color:red;\">Monica:</span>"},
			{"\nPhoebe:", "<br><span style=\"color:red;\">Phoebe:</span>"},
			{"\nChandler:", "<br><span style=\"color:red;\">Chandler:</span>"},
			{"\nRoss:", "<br><span style=\"color:red;\">Ross:</span>"},
			{"\nRachel:", "<br><span style=\"color:red;\">Rachel:</span>"}
			};
	@RequestMapping(value = "/addArticle", method=RequestMethod.GET)
	public ModelAndView view() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("articleInput");
		return mv;
	}

	@RequestMapping(value = "/addArticle", method=RequestMethod.POST)
	public ModelAndView addArticle(@RequestParam(value = "title", required = true) String title,
			@RequestParam(value = "content", required = true) String content, @RequestParam(value = "friends", required = true) boolean friends) {
		ModelAndView mv = new ModelAndView();
		Article article = new Article();
		article.setTitle(title);
		if (friends) {
			for (String[] names : FRIENDS_NAMES) {
				content = content.replaceAll(names[0], names[1]);
			}
		}
		article.setContent(content);
		articleService.save(article);
		if (friends) {
			for (String[] names : FRIENDS_NAMES_VIEW) {
				article.setContent(article.getContent().replaceAll(names[0], names[1]));
			}
		}
		mv.addObject("article", article);
		mv.setViewName("articleView");
		return mv;
	}
}
