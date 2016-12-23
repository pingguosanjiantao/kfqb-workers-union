package cn.edu.ycm.union.db;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class TestMongoDB {

	public static void main(String[] args) {
		try {
			// 连接到 mongodb 服务
			MongoClient mongoClient = new MongoClient("localhost", 27017);
			
			// 连接到数据库
			MongoDatabase mongoDatabase = mongoClient.getDatabase("mycol");
			System.out.println("Connect to database successfully");
			MongoCollection<Document> collection = mongoDatabase.getCollection("test");
			System.out.println("集合 test 选择成功");
//			Document document = new Document("title", "MongoDB").append("description", "database").append("likes", 100)
//					.append("by", "Fly");
//			List<Document> documents = new ArrayList<Document>();
//			documents.add(document);
//			collection.insertMany(documents);
			String json = "{'id':1, 'members':[{'name':'BuleRiver1', 'age':27, 'gender':'M'}, {'name':'BuleRiver2', 'age':23, 'gender':'F'}, {'name':'BuleRiver3', 'age':21, 'gender':'M'}]}";
			
			collection.insertOne(Document.parse(json));
			System.out.println("文档插入成功");
			//db.XXX.insert({"id":1, "members":[{"name":"BuleRiver1", "age":27, "gender":"M"}, {"name":"BuleRiver2", "age":23, "gender":"F"}, {"name":"BuleRiver3", "age":21, "gender":"M"}]});
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}

	}

}
