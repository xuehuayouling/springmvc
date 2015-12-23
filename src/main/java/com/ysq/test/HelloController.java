package com.ysq.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ysq.test.model.User;

@Controller
public class HelloController {
	@RequestMapping("/hello")
	public ModelAndView hello() {
		ModelAndView mv = new ModelAndView();
		User user = new User();
		user.setName("name1");
		user.setPassword("password1");
		mv.addObject("spring", "spring mvc");
		mv.addObject("user", user);
		mv.setViewName("hello");
		return mv;
	}
	@RequestMapping("/hello1")
	public String hello1() {
		return "你好啊";
	}
}
