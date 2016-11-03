package com.ysq.test.controller;

import com.ysq.test.entity.BamMenu;
import com.ysq.test.entity.User;
import com.ysq.test.other.DescribableEnum;
import com.ysq.test.other.JsonResult;
import com.ysq.test.service.BamMenuService;
import com.ysq.test.service.UserService;
import com.ysq.test.util.CodeUtil;
import com.ysq.test.util.TextUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by ysq on 16/11/2.
 */
@Controller
@RequestMapping("/bammenu")
public class BamMenuController {
    @Resource(name = "userService")
    private UserService userService;
    @Resource(name = "bamMenuService")
    private BamMenuService bamMenuService;

    @RequestMapping(value = "/getBamMenuListByUserToken")
    @ResponseBody
    public Object getBamMenuList(String token, HttpServletRequest request) {
        User user = userService.queryByToken(token);
        if (user == null) {
            return new JsonResult(DescribableEnum.NOTLOGIN);
        } else {
            return new JsonResult(DescribableEnum.SUCCESS, bamMenuService.queryBamMenuByUserId(user.getId()));
        }
    }

    @RequestMapping(value = "/addMenu")
    @ResponseBody
    public Object addMenu(String name, String parentId, HttpServletRequest request) {
        if (TextUtil.isEmpty(name)) {
            return new JsonResult(DescribableEnum.MENUNAMECANNOTBENULL);
//        } else if (TextUtil.isEmpty(parentId)) {
//            return new JsonResult(DescribableEnum.PARENTMENUIDCANNOTBENULL);
        } else if (!TextUtil.isEmpty(parentId) && bamMenuService.query(parentId) == null) {
            return new JsonResult(DescribableEnum.PARENTMENUNOTEXIST);
        } else if (bamMenuService.isExist(name ,parentId)) {
            return new JsonResult(DescribableEnum.MENUEXIST);
        }
        String code = CodeUtil.getNext(bamMenuService.getMaxCodeByParentId(parentId));
        BamMenu parent = bamMenuService.query(parentId);
        if (parent != null) {
            code = parent.getCode() + code;
        }
        BamMenu bamMenu = new BamMenu();
        bamMenu.setName(name);
        bamMenu.setCode(code);
        bamMenu.setParentId(parentId);
        bamMenu.setId(TextUtil.getUuId());
        if (bamMenuService.save(bamMenu)) {
            return new JsonResult(DescribableEnum.SUCCESS, bamMenu);
        }
        return new JsonResult(DescribableEnum.SYSTEM_ERROR);
    }
}
