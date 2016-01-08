package com.ysq.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ysq.test.service.AssetService;
import com.ysq.test.util.JsonUtil;

@Controller
@RequestMapping("/access")
public class AccessController {
	@Autowired
	private AssetService mAssetService;
	@RequestMapping(value = "/test")
	public ModelAndView test() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("message", "test success");
		mv.setViewName("hello");
		return mv;
	}
	
	@RequestMapping(value = "/asset_list_view")
	public ModelAndView assetListView() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("message", "test success");
		mv.setViewName("assetList");
		return mv;
	}
	
	@RequestMapping(value = "/asset_list")
	@ResponseBody
	public String assetList() {
		return JsonUtil.getJsonFromObject(mAssetService.getAssetList());
	}

}
