package com.ysq.test.other;

public enum DescribableEnum implements Describable {
    SUCCESS(1, "成功"),

    // 取值范围10000-19999
    // 11000-11999  禁止类枚举值
    // 12000-12999 提醒类枚举值
    PARENTMENUNOTEXIST(11015, "上级菜单不存在"),
    PARENTMENUIDCANNOTBENULL(110014, "上级菜单ID不能为空"),
    MENUEXIST(110013, "菜单已存在"),
    MENUNAMECANNOTBENULL(110012, "菜单名称不能为空"),
    NOTLOGIN(11011, "未登录或登录超时"),
    PARENTTEAMNOTEXIST(11010, "上级组织不存在"),
    PARENTTEAMIDCANNOTBENULL(11009, "上级组织ID不能为空"),
    TEAMEXIST(11008, "组织已存在"),
    TEAMNAMECANNOTBENULL(11007, "组织名称不能为空"),
    ROLENAMEEXIST(11006, "角色名称已存在"),
    ROLENAMECANNOTBENULL(11005, "角色名称不能为空"),
    USERINVALID(11004, "用户名或密码不对"),
    SYSTEM_ERROR(11003, "系统异常"),
    USERNAMECANNOTBENULL(11002, "用户名不能为空"),
    PASSWORDCANNOTBENULL(11001, "密码不能为空"),
    USERNAMEEXIST(11000, "用户名已存在");

    private Integer code;// 描述编码
    private String msg;// 描述信息

    private DescribableEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}