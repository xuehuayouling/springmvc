package com.ysq.test.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.ysq.test.service.RoleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ysq.test.entity.OutputDTO;
import com.ysq.test.entity.User;
import com.ysq.test.service.UserService;
import com.ysq.test.util.JsonUtil;

@Controller
@RequestMapping("/login")
public class LoginController {
    @Resource(name = "userService")
    private UserService service;
    @Resource(name = "roleService")
    private RoleService roleService;

    @RequestMapping(value = "/test")
    public ModelAndView count(@RequestParam(value = "id") long id) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("message", JsonUtil.getJsonFromObject(roleService.queryUserById(id)));
        mv.setViewName("hello");
        return mv;
    }

    @RequestMapping(value = "/login")
    @ResponseBody
    public String valid(@RequestParam(value = "name") String name,
                        @RequestParam(value = "password") String password, HttpServletRequest request) {
        return tryLogin(name, password, request.getSession().getId());
    }

    private String tryLogin(String name, String password, String sessionID) {
        OutputDTO dto = new OutputDTO();
        try {
            final User user = service.login(name, password, sessionID);
            boolean isSuccess = user != null;
            if (isSuccess) {
                dto.setResult(user.getToken());
            } else {
                dto.setSuccess(0);
                dto.setErrmsg("用户名或密码不对");
            }
        } catch (Exception e) {
            dto.setSuccess(0);
            dto.setErrmsg(e.getMessage());
            e.printStackTrace();
        }
        return JsonUtil.getJsonFromObject(dto);
    }

}
