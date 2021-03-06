package com.workflow.component;

import com.workflow.service.Helper;

public class App {
	public static void main(String[] args) {
		
		Helper help = new Helper();
		
		Entity con = new Entity();
		con.addKeyValue("filepath", "temp.csv");
		
		//CsvReader csv = new CsvReader();
		Component csv = (Component) help.getObjectByClassName("com.workflow.component.CsvReader");
		csv.init(con);
		
		Entity mongocon = new Entity();
		mongocon.addKeyValue("host", "localhost");
		mongocon.addKeyValue("port", 27017);
		mongocon.addKeyValue("DBName", "TEMP");
		mongocon.addKeyValue("CollectionName", "Numbers");
		
		//MongoWriter mw = new MongoWriter();
		Component mw = (Component) help.getObjectByClassName("com.workflow.component.MongoWriter");
		mw.init(mongocon);
		
		Entity out;
		
		while((out = csv.process(null))!=null) {
			mw.process(out);
		}
		
		
	}
}
