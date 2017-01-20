package cn.edu.ycm.union.dto;

import java.util.List;

public class ReturnMsg {
	
	private String status;
	
	private String name;
	
	private String msg;

	private String jumpurl;
	
	private List<UserInfo> users;
	
	private List<TaskTemplate> tasks;
	
	private UserInfo user;
	
	private TaskTemplate task;
	
	public List<UserInfo> getUsers() {
		return users;
	}

	public void setUsers(List<UserInfo> users) {
		this.users = users;
	}



	public UserInfo getUser() {
		return user;
	}

	public void setUser(UserInfo user) {
		this.user = user;
	}



	public List<TaskTemplate> getTasks() {
		return tasks;
	}

	public void setTasks(List<TaskTemplate> tasks) {
		this.tasks = tasks;
	}

	public TaskTemplate getTask() {
		return task;
	}

	public void setTask(TaskTemplate task) {
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
