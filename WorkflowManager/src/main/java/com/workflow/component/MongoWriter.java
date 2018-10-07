package com.workflow.component;


import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.*;

public class MongoWriter implements Component{

	MongoClient mongo;
	MongoDatabase db;
	MongoCollection<Document> collection;

	@Override
	public Entity process(Entity input) {
		Document document = new Document(input.getEntity());
		collection.insertOne(document);
		System.out.println("Writing to Mongo "+ document);
		return null;
	}

	@Override
	public boolean init(Entity config) {
		mongo = new MongoClient((String)config.getObjectByName("host"),(Integer)config.getObjectByName("port"));
		db = mongo.getDatabase((String)config.getObjectByName("DBName"));
		collection = db.getCollection((String)config.getObjectByName("CollectionName"));
		return true;
	}

}
