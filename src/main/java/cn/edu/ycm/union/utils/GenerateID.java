package cn.edu.ycm.union.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

public class GenerateID {
	public static String getID(){
		Date date=new Date();
		SimpleDateFormat df=new SimpleDateFormat("yyyyMMddHHmmss");
		return df.format(date);
	}
	
	public static void main(String[] args){
		Map<String , Double> map =  new TreeMap<String , Double>();     
        map.put("0299" , 89.0);     
        map.put("0488" , 80.0);     
        map.put("4119" , 80.0);     
        map.put("1023" , 89.0);     
        System.out.println(map);   
	}
}
