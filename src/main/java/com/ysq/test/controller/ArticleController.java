package com.ysq.test.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ysq.test.entity.Article;
import com.ysq.test.entity.ArticleWithOverview;
import com.ysq.test.service.ArticleService;
import com.ysq.test.util.JsonUtil;

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
	public static final String[][] P_VIEW = {
			{"\t", "</p><p style=text-indent:2em;>"}
			};
	@RequestMapping(value = "/addArticle", method=RequestMethod.GET)
	public ModelAndView view() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("articleInput");
		return mv;
	}
	
	@RequestMapping(value = "/test")
	public ModelAndView test() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("app");
		return mv;
	}
	
	@RequestMapping(value = "/login")
	@ResponseBody
	public String login() {
		String str = "success";
		return str;
	}

	@RequestMapping(value = "/article")
	public ModelAndView article() {
		ModelAndView mv = new ModelAndView();
		Article article = articleService.queryList().get(0);
		StringBuffer buffer = new StringBuffer();
		for (String str : article.getContent().split("[ ,\\n]")){
			if (str.startsWith("\t")) {
				buffer.append("\t").append("<a onclick=clickborder('").append(str.substring(2)).append("')>").append(str.substring(2)).append("</a>").append(" ");
			} else if (str.endsWith(".")) {
				buffer.append("<a onclick=clickborder('").append(str.substring(0, str.length() - 1)).append("')>").append(str.substring(0, str.length() - 1)).append("</a>").append(".").append(" ");
			} else {
				buffer.append("<a onclick=clickborder('").append(str).append("')>").append(str).append("</a>").append(" ");
			}
		}
		article.setContent(buffer.toString());
		for (String[] names : P_VIEW) {
			article.setContent(article.getContent().replaceAll(names[0], names[1]));
		}
		mv.addObject("article", article);
		mv.setViewName("articleView");
	    return mv;
	}
	
	@RequestMapping(value = "/list")
	@ResponseBody
	public String list() {
		List<Article> articles = articleService.queryList();
		List<ArticleWithOverview> articleWithOverviews = new ArrayList<>();
		for (Article article : articles) {
			ArticleWithOverview articleWithOverview = new ArticleWithOverview();
			articleWithOverview.setId(article.getId());
			articleWithOverview.setTitle(article.getTitle());
			articleWithOverview.setOverview(article.getContent().substring(0, Math.min(75, article.getContent().length())));
			articleWithOverview.setContent(article.getContent());
			for (String[] names : FRIENDS_NAMES_VIEW) {
				articleWithOverview.setContent(articleWithOverview.getContent().replaceAll(names[0], names[1]));
			}
			articleWithOverviews.add(articleWithOverview);
		}
	    return JsonUtil.getJsonFromObject(articleWithOverviews);
	}
	
	@RequestMapping(value = "/addArticle", method=RequestMethod.POST)
	public ModelAndView addArticle(@RequestParam(value = "title", required = true) String title,
			@RequestParam(value = "content", required = true) String content, @RequestParam(value = "friends", required = false) boolean friends) {
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
		} else {
			for (String[] names : P_VIEW) {
				article.setContent(article.getContent().replaceAll(names[0], names[1]));
			}
		}
		mv.addObject("article", article);
		mv.setViewName("articleView");
		return mv;
	}
}
