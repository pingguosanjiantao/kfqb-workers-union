package cn.edu.ycm.union.utils;

public class TaskCell {
	
	private String type = TaskCellType.DEFAULT;
	private String value;
	
	public TaskCell(){
		this.type = "";
		this.value = "";
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
