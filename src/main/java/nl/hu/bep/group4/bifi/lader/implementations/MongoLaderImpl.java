package nl.hu.bep.group4.bifi.lader.implementations;

import nl.hu.bep.group4.bifi.lader.MongoLader;

import java.util.Date;
import java.util.Iterator;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoLaderImpl implements MongoLader {
	MongoDatabase db = null;

	public MongoDatabase connectToMongoDB() {
		try {
			System.out.println("begin connecting");
			
			String database = "BEPBifi";
			
			MongoClientURI uri = new MongoClientURI("mongodb+srv://dbUser:112112@cluster0-vk3z3.mongodb.net/test?retryWrites=true");
			MongoClient mongoClient = new MongoClient(uri);
			db = mongoClient.getDatabase(database);
			MongoCollection<Document> xy = db.getCollection("BEPBifi");
			
//			Iterator<Document> it = xy.find().iterator();
//			
//			System.out.println(it.hasNext());
//			
//			while(it.hasNext()){
//				Document mail = it.next();
//				//String subject = mail.getString("date");
//				Date date = mail.getDate("date");
//				System.out.println(date);
//			}
//			
//			mongoClient.close();
//			
//			System.out.println("connection closed");
//			
		}catch(Exception e) {
			System.out.println("exception in handling the request. Exception = " + e);
		}
		return db;
	}
	
	
	
	
	
	
}
