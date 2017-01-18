package cn.edu.ycm.union.dto;

public class TaskTemplate {
	
	//任务编号
	private String taskID;
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
