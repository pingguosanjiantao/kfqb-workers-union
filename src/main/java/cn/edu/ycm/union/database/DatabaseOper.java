package cn.edu.ycm.union.database;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import cn.edu.ycm.union.dto.UserInfo;

public class DatabaseOper {
	
	private static final String DB_NAME = "workers_union_product2";
	private static final String DB_ADDRESS = "127.0.0.1";
	
	private static final Logger logger = LoggerFactory.getLogger(DatabaseOper.class);
	
	//未含有该数据库
	private static boolean notHasDatabase(MongoClient mongoClient,String dbName){
		MongoCursor<String> databaseNamesItor = mongoClient.listDatabaseNames().iterator();
		while(databaseNamesItor.hasNext()){
			String tmp = databaseNamesItor.next();
			if(dbName.equals(tmp)){
				return false;
			}
		}
		return true;
	}
	
	//未含有该集合
	private static boolean notHasCollection(MongoDatabase database, String collectionName){
		MongoCursor<String> datbaseNamesItor = database.listCollectionNames().iterator();
		while(datbaseNamesItor.hasNext()){
			String tmp = datbaseNamesItor.next();
			if(collectionName.equals(tmp)){
				return false;
			}
		}
		return true;
	}
	
	//创建集合
	private static void createCollection(MongoDatabase database,String dbName){
		database.createCollection(dbName);
	}
	
	//未含有超级管理员
	private static boolean notHasAdmin(MongoDatabase database){
		MongoCollection<Document> collection = database.getCollection("users");
		Bson arg0 = Filters.eq("id", UserInfo.getAdmin().getId());
		MongoCursor<Document> ret = collection.find(arg0).iterator();
		if(ret.hasNext()){
			return false;
		}
		return true;
	}
	
	//创建超级管理员
	private static void createAdmin(MongoDatabase database){
		MongoCollection<Document> collection = database.getCollection("users");
		collection.insertOne(Document.parse(new Gson().toJson(UserInfo.getAdmin())));
	}
	
	//手工初始化数据库
	public static void initializedDB(){
		
		//新建一个数据库实例
		MongoClient mongoClient = MongoFacorty.getInstance();
		
		//检验数据库是否存在
		logger.info("检验数据库"+DB_NAME+"是否存在");
		if(notHasDatabase(mongoClient,DB_NAME)){
			logger.info("数据库"+DB_NAME+"还未创建，请先创建");
			return;
		}
		logger.info("数据库"+DB_NAME+"存在");
		
		MongoDatabase database = mongoClient.getDatabase(DB_NAME);

		//检验必须的集合是否存在
		logger.info("检验集合users是否存在");
		if(notHasCollection(database,"users")){
			logger.info("集合users不存在，开始创建");
			createCollection(database,"users");
		}
		logger.info("集合users已经存在");
		
		logger.info("检验集合tasks是否存在");
		if(notHasCollection(database,"tasks")){
			logger.info("集合tasks不存在，开始创建");
			createCollection(database,"tasks");
		}
		logger.info("集合tasks已经存在");
		
		//检验超管是否存在
		logger.info("检验超级管理员是否存在");
		if(notHasAdmin(database)){
			logger.info("超级管理员不存在，开始创建");
			createAdmin(database);
		}
		logger.info("超级管理员已经存在");
		
	}
	
	//释放数据库
	public static void dropDB(){
		MongoClient mongoClient = MongoFacorty.getInstance();
		mongoClient.close();
	}
	
	//连接池设置为单例模式
	private static class MongoFacorty{
		
		private static MongoClient mongoClient;
		
		static{
			MongoClientOptions.Builder build = new MongoClientOptions.Builder();        
            build.connectionsPerHost(10);   //与目标数据库能够建立的最大connection数量为50
            build.threadsAllowedToBlockForConnectionMultiplier(50); //如果当前所有的connection都在使用中，则每个connection上可以有50个线程排队等待
            build.maxWaitTime(1000*60*2);//最大等待时间
            build.connectTimeout(1000*60*1);//与数据库建立连接的timeout
            MongoClientOptions myOptions = build.build();       
            try {
                mongoClient = new MongoClient(DB_ADDRESS, myOptions);          
            } catch (Exception e) {
            		System.out.println("数据库连接异常");
                e.printStackTrace();
            } 
		}
		
		public static MongoClient getInstance(){
			return mongoClient;
		}
	}
	
	//连接获取数据库
	private MongoDatabase ConnectAndGetDatabase(){
		//新建一个数据库客户端，获取数据库
		MongoClient mongoClient = MongoFacorty.getInstance();
		return mongoClient.getDatabase(DB_NAME);
	}
	
	//根据collection名返回集合
	private MongoCollection<Document> getCollection(String collectionName){
		return ConnectAndGetDatabase().getCollection(collectionName);
	}
	
	//查
	public FindIterable<Document> getDocumentByBson(String collectionName, Bson bson){
		MongoCollection<Document> collection  = getCollection(collectionName);
		return collection.find(bson);
	}
	
	//全查
	public FindIterable<Document> getDocument(String collectionName){
		MongoCollection<Document> collection  = getCollection(collectionName);
		return collection.find();
	}
	
	//改
	public void updateDocument(String collectionName, Bson bson, String json){
		MongoCollection<Document> collection  = getCollection(collectionName);
		collection.findOneAndReplace(bson, Document.parse(json));
	}
	
	//增
	public void insertDocument(String collectionName, String json){
		MongoCollection<Document> collection  = getCollection(collectionName);
		collection.insertOne(Document.parse(json));
	}
	
	//删
	public void deleteDocument(String collectionName, String json){
		MongoCollection<Document> collection  = getCollection(collectionName);
		collection.insertOne(Document.parse(json));
	}
	
	//增表
	public void createCollection(String collectionName){
		ConnectAndGetDatabase().createCollection(collectionName);
	}
	
	//删表
	public void deleteCollection(String collectionName){
		ConnectAndGetDatabase().getCollection(collectionName).drop();
	}
}
