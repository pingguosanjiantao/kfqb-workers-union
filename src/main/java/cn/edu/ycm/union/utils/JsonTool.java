package cn.edu.ycm.union.utils;

import com.google.gson.Gson;

public class JsonTool {
	
	/**
	 * 对象转json化的string
	 * @param ob 对象
	 * @return
	 */
	public static String Json2String(Object ob){
		return new Gson().toJson(ob);
	}
	
	/**
	 * json化string转对象
	 * @param json json字符串
	 * @param obClass 对象class
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Object String2Json(String json, Class obClass){
		return new Gson().fromJson(json, obClass);
	}
	
}
