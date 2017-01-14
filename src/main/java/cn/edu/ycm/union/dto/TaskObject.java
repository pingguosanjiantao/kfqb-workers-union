package cn.edu.ycm.union.dto;

public class TaskObject {
	
	//任务标题
	private String titile;
	//填写说明
	private String instruction;
	//用户
	private UserInfo user;
	//用户填写数据
	private TaskTable table;
	
	public String getTitile() {
		return titile;
	}
	public void setTitile(String titile) {
		this.titile = titile;
	}
	public String getInstruction() {
		return instruction;
	}
	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}
	public UserInfo getUser() {
		return user;
	}
	public void setUser(UserInfo user) {
		this.user = user;
	}
	public TaskTable getTable() {
		return table;
	}
	public void setTable(TaskTable table) {
		this.table = table;
	}
}
