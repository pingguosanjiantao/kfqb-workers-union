package cn.edu.ycm.union.dto;

public class TaskTemplate {
	
	//任务编号
	private String taskID;
	//创建者
	private String createrName;
	//有效
	private String validFlag;
	public String getCreaterName() {
		return createrName;
	}
	public void setCreaterName(String createrName) {
		this.createrName = createrName;
	}
	public String getValidFlag() {
		return validFlag;
	}
	public void setValidFlag(String validFlag) {
		this.validFlag = validFlag;
	}
	//任务对象
	private TaskObject taskObject;
	
	public String getTaskID() {
		return taskID;
	}
	public void setTaskID(String taskID) {
		this.taskID = taskID;
	}
	public TaskObject getTaskObject() {
		return taskObject;
	}
	public void setTaskObject(TaskObject taskObject) {
		this.taskObject = taskObject;
	}
}
