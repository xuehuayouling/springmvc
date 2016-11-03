package com.ysq.test.other;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;


/**
 * @ClassName: JsonResult
 * @Description: 用户后台向前台返回的Json对象
 * @author: 崔科峰
 * @date: 2015年12月15日 下午4:52:11
 * @Company: 奥克斯医信公司
 * @version: v1.0
 */
public class JsonResult implements Serializable {

    private static final long serialVersionUID = 1L;

    private String message; // 接口调用结果描述信息

    private Integer statusCode; // 接口调用结果描述编码;默认0表示成功


    private Object data; // 接口返回的数据

    public JsonResult(Describable describable) {
        this.message = describable.getMsg();
        this.statusCode = describable.getCode();
    }

    public JsonResult(Describable describable, Object data) {
        this.message = describable.getMsg();
        this.statusCode = describable.getCode();
        this.data = data;
    }

    public JsonResult(Integer statusCode, String message) {
        this.message = message;
        this.statusCode = statusCode;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
