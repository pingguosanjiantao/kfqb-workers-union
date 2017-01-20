package cn.edu.ycm.union.utils;

import cn.edu.ycm.union.dto.ReturnMsg;

public class ReturnTool {
	
	/**
	 * 获取失败返回对象
	 * @param msg
	 * @return
	 */
	public static ReturnMsg getFailedReturn(String msg){
		ReturnMsg ret = new ReturnMsg();
		ret.setStatus("1");
		ret.setMsg(msg);
		return ret;
	}
	
	/**
	 * 获取成功返回对象
	 * @return
	 */
	public static ReturnMsg getSuccReturn(){
		ReturnMsg ret = new ReturnMsg();
		ret.setStatus("0");
		return ret;
	}
	
	/**
	 * 获取失败返回对象String
	 * @param msg
	 * @return
	 */
	public static String getFailedStringReturn(String msg){
		ReturnMsg ret = new ReturnMsg();
		ret.setStatus("1");
		ret.setMsg(msg);
		return JsonTool.Json2String(ret);
	}
	
	/**
	 * 获取成功返回对象String
	 * @return
	 */
	public static String getSuccStringReturn(){
		ReturnMsg ret = new ReturnMsg();
		ret.setStatus("0");
		return JsonTool.Json2String(ret);
	}
	
}
