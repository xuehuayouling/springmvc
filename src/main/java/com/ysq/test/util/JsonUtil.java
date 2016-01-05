package com.ysq.test.util;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * fastjson alibaba 将对象转换为Json对象
 * 
 * @author tangtulip@163.com 2012-1-12
 * 
 */
public class JsonUtil {

	/**
	 * 将对象转换json字符串 如果存在开头结束没有'[' ']'补充此符号
	 * 
	 * @param obj
	 * @return
	 */
	public static String getJsonFromObject(Object obj) {
		String json = "";
		if (obj instanceof ArrayList) {
			json = JSONArray.toJSONString(obj);
		} else {
			json = JSON.toJSONString(obj);
		}
		return json;
	}

	/**
	 * 转成具体实体对象集 格式：[model.PatientYdhlDTO@12a0f6c]
	 * 
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static List JsonToFormList(String json, Class clazz) {
		if (null ==json || json.equals("") || null == clazz)
			return null;
		List list = JSON.parseArray(json, clazz);
		return list;
	}
	
	/**
	 * 转成具体实体对象集 格式：[model.PatientYdhlDTO@12a0f6c]
	 * 
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static List JsonToFormList(String json, String startFlag,Class clazz) {
		if (null ==json || json.equals("") || null == clazz)
			return null;
		JSONObject jsonObj = JSON.parseObject(json);  
		JSONArray result = jsonObj.getJSONArray(startFlag);  
		if(null == result)
			return null;
		List list = JSON.parseArray(result.toJSONString(), clazz);
		return list;
	}

	/**
	 * 转成具体实体对象集 格式：[model.PatientYdhlDTO@12a0f6c]
	 * 
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static Object JsonToFormObject(String json, Class clazz) {
		if (null ==json || json.equals("") || null == clazz)
			return null;
		Object obj = JSON.parseObject(json, clazz);
		return obj;
	}
	
	/**
	 * 转成具体实体对象集 格式：[model.PatientYdhlDTO@12a0f6c]
	 * 
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static Object JsonToFormObject(String json,String startFlag, Class clazz) {
		if (null ==json || json.equals("") || null == clazz)
			return null;
		JSONObject jsonObj = JSON.parseObject(json);  
		JSONObject result = jsonObj.getJSONObject(startFlag);  
		Object obj= JSON.parseObject(result.toJSONString(),clazz);  
		return obj;
	}

	/**
	 * 转成对象数据集 数据集格式如：[{"age":0,"charge":10}, {"age":9,"charge":10}]
	 * 
	 * @param json
	 * @return
	 */
	public static List toList(String json) {
		if (null ==json || json.equals(""))
			return null;
		List result = JSONObject.parseArray(json, List.class);
		return result;
	}
	
	public static void main(String[] args) {
		// Person dto=new Person();
		// dto.setAge(10);
		// dto.setName("aa");
		// String ss = getJsonFromObject2(dto);
		// System.out.println("单对象："+ ss);
		// String ss2 = getJsonFromObject(dto);
		// System.out.println("数据组方式:"+ss2);
		// Person s = (Person)JsonToFormObject(ss, Person.class);
		// System.out.println(s.toString());
		// List list = JsonToFormList(ss2, Person.class);
		// System.out.println(list.get(0).toString());
	}

}
