package cn.edu.ycm.union.utils;

import java.io.IOException;

import cn.edu.ycm.union.database.DatabaseOper;

public class TestExcel {

	public static void main(String[] args) throws IOException {
		TaskTemplate myTem = new TaskTemplate();
		myTem.initializedFromExcel("/Users/yangchengmin/Documents/test.xlsx");
		DatabaseOper db = new DatabaseOper();
		db.InsertTaskTemplate(myTem);
		
	}
}
