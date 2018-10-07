package com.workflow.component;

public class App {
	public static void main(String[] args) {
		Entity con = new Entity();
		con.addKeyValue("filepath", "temp.csv");
		
		CsvReader csv = new CsvReader();
		csv.init(con);
		
		Entity mongocon = new Entity();
		mongocon.addKeyValue("host", "localhost");
		mongocon.addKeyValue("port", 27017);
		mongocon.addKeyValue("DBName", "TEMP");
		mongocon.addKeyValue("CollectionName", "Numbers");
		
		MongoWriter mw = new MongoWriter();
		mw.init(mongocon);
		
		Entity out;
		
		while((out = csv.process(null))!=null) {
			mw.process(out);
		}
		
		
	}
}
