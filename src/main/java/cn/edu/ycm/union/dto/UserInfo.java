package cn.edu.ycm.union.dto;

public class UserInfo {
	//姓名
	private String name;
	//工号
	private String id;
	//团队
	private String team;
	//手机号
	private String cellphone;
	//是否是管理员
	private String adminFlag;
	
	//取模板用户
	public static UserInfo getTaskTemplateUser(){
		UserInfo ret = new UserInfo();
		ret.setId("0000");
		return ret;
	}
	
	//取超级管理员
	public static UserInfo getAdmin(){
		UserInfo ret = new UserInfo();
		ret.setId("0000");
		ret.setName("超级管理员");
		ret.setTeam("部门管理团队");
		ret.setAdminFlag("true");
		ret.setCellphone("1234567");
		return ret;
	}
	
	public String getAdminFlag() {
		return adminFlag;
	}
	public void setAdminFlag(String adminFlag) {
		this.adminFlag = adminFlag;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTeam() {
		return team;
	}
	public void setTeam(String team) {
		this.team = team;
	}
	public String getCellphone() {
		return cellphone;
	}
	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}
	
}
