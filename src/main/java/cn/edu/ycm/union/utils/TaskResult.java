package cn.edu.ycm.union.utils;

import java.util.List;

public class TaskResult {
	
	private String uid;//uid的该用户填写结果，录入task_taskID的Collection
	private String name;
	private List<TaskRow> table;
	
	public String getUid() {
		return uid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public List<TaskRow> getTable() {
		return table;
	}
	public void setTable(List<TaskRow> table) {
		this.table = table;
	}
}
