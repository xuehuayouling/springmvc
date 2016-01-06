package com.ysq.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/access")
public class AccessController {

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public ModelAndView count() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("message", "test success");
		mv.setViewName("hello");
		return mv;
	}

}
