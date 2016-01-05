package com.ysq.test.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ysq.test.entity.OutputDTO;
import com.ysq.test.service.UserService;
import com.ysq.test.util.JsonUtil;

@Controller
@RequestMapping("/login")
public class LoginController {
	@Resource(name = "userService")
	private UserService service;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public String validPost(@RequestParam(value = "password") String password,
			@RequestParam(value = "name") String name) {
		return tryLogin(name, password);
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	@ResponseBody
	public String validGet(@RequestParam(value = "password") String password,
			@RequestParam(value = "name") String name) {
		return tryLogin(name, password);
	}
	
	@RequestMapping(value = "/count", method = RequestMethod.GET)
	public ModelAndView count() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("message", "test success");
		mv.setViewName("hello");
		return mv;
	}
	
	private String tryLogin(String name, String password) {
		OutputDTO dto = new OutputDTO();
		try {
			boolean isSuccess = service.login(name, password);
			if (isSuccess) {
				dto.setResult("success");
			} else {
				dto.setSuccess(0);
				dto.setErrmsg("用户名或密码不对");
			}
		} catch (Exception e) {
			dto.setSuccess(0);
			dto.setErrmsg(e.getMessage());
		}
		return JsonUtil.getJsonFromObject(dto);
	}

}
