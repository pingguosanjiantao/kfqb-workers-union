package cn.edu.ycm.union.dto;

public class TaskData {
	
	//用户id
	private String userID;
	//该用户填写的数据
	private TaskObject taskObject;
	
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public TaskObject getTaskObject() {
		return taskObject;
	}
	public void setTaskObject(TaskObject taskObject) {
		this.taskObject = taskObject;
	}
}
