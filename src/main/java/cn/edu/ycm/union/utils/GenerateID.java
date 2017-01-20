package cn.edu.ycm.union.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;

import cn.edu.ycm.union.dto.TaskObject;
import cn.edu.ycm.union.dto.TaskRow;
import cn.edu.ycm.union.dto.TaskTable;
import cn.edu.ycm.union.dto.TaskTemplate;

public class GenerateID {
	public static String getID(){
		Date date=new Date();
		SimpleDateFormat df=new SimpleDateFormat("yyyyMMddHHmmss");
		return df.format(date);
	}
	
	public static void main(String[] args){
		List<TaskRow> lines = new ArrayList<>();
		TaskRow t1 = new TaskRow();
		TaskRow t2 = new TaskRow();
		TaskRow t3 = new TaskRow();
		t1.setKey("橡皮");
		t2.setKey("铅笔");
		t3.setKey("尺子");
		t1.setTip("个");
		t2.setTip("枚");
		t3.setTip("口");
		t1.setValue("");
		t1.setValueType("");
		t1.setValueTypeText("");
		t2.setValue("");
		t2.setValueType("");
		t2.setValueTypeText("");
		t3.setValue("");
		t3.setValueType("");
		t3.setValueTypeText("");
		lines.add(t1);
		lines.add(t2);
		lines.add(t3);
		
		TaskTable taskTable = new TaskTable();
		taskTable.setLines(lines);
		
		TaskObject taskObject = new TaskObject();
		taskObject.setTaskTitle("文具");
		taskObject.setInstruction("截止12号");
		taskObject.setTable(taskTable);
		TaskTemplate a = new TaskTemplate();
		a.setTaskID("123");
		a.setCreaterName("mr yang");
		a.setValidFlag("true");
		a.setTaskObject(taskObject);
		System.out.println(new Gson().toJson(a));
		System.out.println(GenerateID.getID());
	}
}
