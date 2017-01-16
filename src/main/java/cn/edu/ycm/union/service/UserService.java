package cn.edu.ycm.union.service;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;

import cn.edu.ycm.union.database.DatabaseOper;
import cn.edu.ycm.union.dto.UserInfo;

public class UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	private DatabaseOper databaseOper = new DatabaseOper();
	
	//新建一个用户
	public void insertUser(UserInfo userInfo){
		logger.info("新建"+userInfo.getId()+"用户");
		databaseOper.insertDocument("users", new Gson().toJson(userInfo));
	}
	
	//删除一个用户
	public void deleteUser(String userID){
		logger.info("删除"+userID+"用户");
		Bson bson = Filters.eq("id",userID);
		databaseOper.deleteDocument("users", bson);
	}
	
	//更新一个用户
	public void updateUserInfo(UserInfo userInfo){
		logger.info("更新"+userInfo.getId()+"信息");
		String json = new Gson().toJson(userInfo);
		Bson bson = Filters.eq("id",userInfo.getId());
		databaseOper.updateDocument("users", bson, json);
	}
	
	//查询一个用户
	public UserInfo getUserInfoByID(String userID){
		logger.info("查询"+userID+"信息");
		Bson bson = Filters.eq("id",userID);
		Document result = databaseOper.getDocumentByBson("users", bson).first();
		if (result == null){
			return null;
		}
		UserInfo ret = new Gson().fromJson(result.toJson(), UserInfo.class);
		return ret;
	}
	
	//查询所有用户
	public List<UserInfo> getUsers(){
		logger.info("查询所有用户信息");
		FindIterable<Document> result = databaseOper.getDocument("users");
		List<UserInfo> ret = new ArrayList<>();
		MongoCursor<Document> cursor = result.iterator();
		while (cursor.hasNext()){
			Document tmp = cursor.next();
			UserInfo user = new Gson().fromJson(tmp.toJson(), UserInfo.class);
			ret.add(user);
		}
		return ret;
	}
	
}
