package com.ysq.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by ysq on 16/10/14.
 */
@Controller
@RequestMapping("/jasminetest")
public class JasmineTestController {
    @RequestMapping(value = "/test")
    public String test() {
        return "jasmine/SpecRunner.html";
    }
}
