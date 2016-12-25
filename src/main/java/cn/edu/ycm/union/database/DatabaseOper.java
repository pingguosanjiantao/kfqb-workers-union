package cn.edu.ycm.union.database;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import cn.edu.ycm.union.utils.TaskTemplate;

public class DatabaseOper {
	
	//连接池设置为单例模式
	private static class MongoFacorty{
		
		private static MongoClient mongoClient;
		
		static{
			MongoClientOptions.Builder build = new MongoClientOptions.Builder();        
            build.connectionsPerHost(50);   //与目标数据库能够建立的最大connection数量为50
            build.threadsAllowedToBlockForConnectionMultiplier(50); //如果当前所有的connection都在使用中，则每个connection上可以有50个线程排队等待
            build.maxWaitTime(1000*60*2);//最大等待时间
            build.connectTimeout(1000*60*1);//与数据库建立连接的timeout
            MongoClientOptions myOptions = build.build();       
            try {
                mongoClient = new MongoClient("127.0.0.1", myOptions);          
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
		return mongoClient.getDatabase("workers_union_product");
	}
	
	//返回任务集合
	private MongoCollection<Document> getTasksCollection(){
		return ConnectAndGetDatabase().getCollection("tasks");
	}
	
	//返回单个任务集合
	public MongoCollection<Document> getTaskById(String taskId){
		return ConnectAndGetDatabase().getCollection("task"+taskId);
	}
	
	//返回用户集合
	public MongoCollection<Document> getUsers(){
		return ConnectAndGetDatabase().getCollection("users");
	}
	
	//向数据库插入task模板数据
	public boolean InsertTaskTemplate(TaskTemplate taskTem){
		MongoCollection<Document> collection = getTasksCollection();
		String json = new Gson().toJson(taskTem);
		collection.insertOne(Document.parse(json));
		return true;
	}
	
	//获取任务列表
	public List<TaskTemplate> getTaskTemplates(){
		List<TaskTemplate> templates = new ArrayList<>();
		MongoCollection<Document> collection = getTasksCollection();
		FindIterable<Document> finder = collection.find();
		MongoCursor<Document> iter = finder.iterator();
		while (iter.hasNext()){
			Document ele = iter.next();
			TaskTemplate taskTemplate = new Gson().fromJson(ele.toJson(), TaskTemplate.class);
			templates.add(taskTemplate);
		}		
		return templates;
	}

	public static void main(String[] a){
		TaskTemplate myTem = new TaskTemplate();
		myTem.initializedFromExcel("/Users/yangchengmin/Documents/test.xlsx");
		DatabaseOper db = new DatabaseOper();
		db.InsertTaskTemplate(myTem);
		
		
		
	}
}
