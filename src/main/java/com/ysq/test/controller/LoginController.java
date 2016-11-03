package com.ysq.test.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.ysq.test.entity.BamMenu;
import com.ysq.test.entity.Role;
import com.ysq.test.entity.Team;
import com.ysq.test.other.DescribableEnum;
import com.ysq.test.other.JsonResult;
import com.ysq.test.service.BamMenuService;
import com.ysq.test.service.RoleService;
import com.ysq.test.service.TeamService;
import com.ysq.test.util.CodeUtil;
import com.ysq.test.util.TextUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ysq.test.entity.User;
import com.ysq.test.service.UserService;

@Controller
@RequestMapping("/login")
public class LoginController {
    @Resource(name = "userService")
    private UserService userService;
    @Resource(name = "roleService")
    private RoleService roleService;
    @Resource(name = "teamService")
    private TeamService teamService;
    @Resource(name = "bamMenuService")
    private BamMenuService bamMenuService;

    @RequestMapping(value = "/addUser")
    @ResponseBody
    public Object addUser(String name, String password, HttpServletRequest request) {
        if (TextUtil.isEmpty(name)) {
            return new JsonResult(DescribableEnum.USERNAMECANNOTBENULL);
        } else if (TextUtil.isEmpty(password)) {
            return new JsonResult(DescribableEnum.PASSWORDCANNOTBENULL);
        } else if (userService.isExist(name)) {
            return new JsonResult(DescribableEnum.USERNAMEEXIST);
        }
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        user.setId(TextUtil.getUuId());
        userService.addUser(user);
        return new JsonResult(DescribableEnum.SUCCESS);
    }

    @RequestMapping(value = "/addRole")
    @ResponseBody
    public Object addRole(String name, HttpServletRequest request) {
        if (TextUtil.isEmpty(name)) {
            return new JsonResult(DescribableEnum.ROLENAMECANNOTBENULL);
        } else if (roleService.isExist(name)) {
            return new JsonResult(DescribableEnum.ROLENAMEEXIST);
        }
        Role role = new Role();
        role.setName(name);
        role.setId(TextUtil.getUuId());
        if (roleService.save(role)) {
            return new JsonResult(DescribableEnum.SUCCESS, role);
        }
        return new JsonResult(DescribableEnum.SYSTEM_ERROR);
    }

    @RequestMapping(value = "/addTeam")
    @ResponseBody
    public Object addTeam(String name, String parentId, HttpServletRequest request) {
        if (TextUtil.isEmpty(name)) {
            return new JsonResult(DescribableEnum.TEAMNAMECANNOTBENULL);
        } else if (TextUtil.isEmpty(parentId)) {
            return new JsonResult(DescribableEnum.PARENTTEAMIDCANNOTBENULL);
        } else if (teamService.query(parentId) == null) {
            return new JsonResult(DescribableEnum.PARENTTEAMNOTEXIST);
        } else if (teamService.isExist(name ,parentId)) {
            return new JsonResult(DescribableEnum.TEAMEXIST);
        }
        String code = CodeUtil.getNext(teamService.getMaxCodeByParentId(parentId));
        Team parent = teamService.query(parentId);
        if (parent != null) {
            code = parent.getCode() + code;
        }
        Team team = new Team();
        team.setName(name);
        team.setCode(code);
        team.setParentId(parentId);
        team.setId(TextUtil.getUuId());
        if (teamService.save(team)) {
            return new JsonResult(DescribableEnum.SUCCESS, team);
        }
        return new JsonResult(DescribableEnum.SYSTEM_ERROR);
    }

    @RequestMapping(value = "/login")
    @ResponseBody
    public Object valid(String name, String password, HttpServletRequest request) {
        return tryLogin(name, password, request.getSession().getId());
    }

    private Object tryLogin(String name, String password, String sessionID) {
        final User user = userService.login(name, password, sessionID);
        boolean isSuccess = user != null;
        if (!isSuccess) {
            return new JsonResult(DescribableEnum.USERINVALID);
        } else {
            return new JsonResult(DescribableEnum.SUCCESS, user);
        }
    }

}
