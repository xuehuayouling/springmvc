package com.ysq.test.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.ysq.test.entity.User;

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
	
	@RequestMapping(value = "/hello")
	public String hello() {
		return "views/hello.html";
	}

	@RequestMapping(value = "/hello1")
	public String hello1() {
		return "views/test/hello.html";
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public Object saveEssays(HttpServletRequest request, HttpServletResponse response, String token) throws Exception {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		List<MultipartFile> files = multipartRequest.getFiles("file");
		return "{'a': '1'}";
	}
	
	@RequestMapping(value = "/testRequestBody", method = RequestMethod.POST)
	@ResponseBody
	public Object testRequestBody(HttpServletRequest request, HttpServletResponse response,@RequestBody User user) throws Exception {
		@SuppressWarnings("unused")
		String token1 = user.getName();
		return user;
	}
	
	@RequestMapping(value = "/testParams", method = RequestMethod.POST)
	@ResponseBody
	public Object testParams(HttpServletRequest request, HttpServletResponse response, String token) throws Exception {
		@SuppressWarnings("unused")
		String token1 = token;
		return token1;
	}

}
