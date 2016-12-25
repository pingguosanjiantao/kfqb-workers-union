package cn.edu.ycm.union.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GenerateID {
	public static String getID(){
		Date date=new Date();
		SimpleDateFormat df=new SimpleDateFormat("yyyyMMddhhmmss");
		return df.format(date);
	}
	
	public static void main(String[] args){
		System.out.println(GenerateID.getID());
	}
}
