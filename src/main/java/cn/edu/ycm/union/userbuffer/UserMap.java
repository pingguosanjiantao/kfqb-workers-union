package cn.edu.ycm.union.userbuffer;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.edu.ycm.union.dto.UserInfo;
import jodd.petite.meta.PetiteBean;

@PetiteBean
public class UserMap {
	
	static final Logger logger = LoggerFactory.getLogger(UserMap.class);
	
	//用户登录缓存：key=id,value=userinfo
	private static Map<String,UserInfo> usersOnline= new HashMap<>();
	
	public UserMap(){
		logger.info("用户登录缓存模块启动");
		
//		UserInfo a = new UserInfo();
//		a.setId("121312");
//		a.setName("zhangsan");
//		
//		usersOnline.put(a.getId(), a);
//		
//		UserInfo tmp = usersOnline.get(a.getId());
//		
//		System.out.println(tmp.getName());
//		System.out.println("查询是否登录"+isLogin(a.getId()));
//		
//		delUserById(a.getId());
//		System.out.println("查询是否登录"+isLogin(a.getId()));
	}
	
	public boolean isLogin(String id){
		if (usersOnline.containsKey(id)){
			return true;
		}
		return false;
	}
	
	public UserInfo getUserInfo(String id){
		return usersOnline.get(id);
	}
	
	//插入，用于用户登录
	public void insertUser(UserInfo user){
		usersOnline.put(user.getId(), user);
	}
	
	//删除，用于用户退出
	public void delUser(UserInfo user){
		usersOnline.remove(user.getId());
	}
	//删除，用于用户退出
	public void delUserById(String Id){
		usersOnline.remove(Id);
	}
}
