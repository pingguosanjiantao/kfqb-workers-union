package cn.edu.ycm.union.service;

import org.bson.conversions.Bson;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.mongodb.client.model.Filters;

import cn.edu.ycm.union.database.DatabaseOper;
import cn.edu.ycm.union.dto.UserInfo;
import jodd.petite.meta.PetiteBean;
import jodd.petite.meta.PetiteInject;
import jodd.petite.scope.ProtoScope;

@PetiteBean(scope=ProtoScope.class)
public class UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	@PetiteInject
	private DatabaseOper databaseOper;
	//新建一个用户
	
	//删除一个用户
	
	//更新一个用户
	
	//查询一个用户
	public UserInfo getUserInfoByID(String userID){
		logger.info("查询"+userID+"信息");
		Bson bson = Filters.eq("id",userID);
		Document result = databaseOper.getDocumentByBson("users", bson).first();
		UserInfo ret = new Gson().fromJson(result.toJson(), UserInfo.class);
		return ret;
	}
	
}
