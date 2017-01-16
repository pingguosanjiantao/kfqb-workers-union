package cn.edu.ycm.union.dto;

import java.util.List;

public class ReturnMsg {
	
	private String status;
	
	private String name;
	
	private String msg;

	private String jumpurl;
	
	private List<UserInfo> users;
	
	private List<TaskObject> tasks;
	
	private UserInfo user;
	
	private TaskObject task;
	
	public List<UserInfo> getUsers() {
		return users;
	}

	public void setUsers(List<UserInfo> users) {
		this.users = users;
	}

	public List<TaskObject> getTasks() {
		return tasks;
	}

	public void setTasks(List<TaskObject> tasks) {
		this.tasks = tasks;
	}

	public UserInfo getUser() {
		return user;
	}

	public void setUser(UserInfo user) {
		this.user = user;
	}

	public TaskObject getTask() {
		return task;
	}

	public void setTask(TaskObject task) {
		this.task = task;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	public String getJumpurl() {
		return jumpurl;
	}

	public void setJumpurl(String jumpurl) {
		this.jumpurl = jumpurl;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
