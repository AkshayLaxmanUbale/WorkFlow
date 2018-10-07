package com.workflow.component;

import java.io.FileReader;
import java.io.IOException;

import com.opencsv.*;

public class CsvReader implements Component{

	String csvFilePath = "";
	CSVReader reader;
	String[] headers;
	
	@Override
	public boolean init(Entity config) {
		csvFilePath = (String) config.getObjectByName("filepath"); 
		try {
			reader = new CSVReader(new FileReader(csvFilePath));
			headers = reader.readNext();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public Entity process(Entity input) {
		Entity output = new Entity();
		String[] record;
		try {
			if((record = reader.readNext())!=null) {
				for(int i=0;i<record.length;i++) {
					output.addKeyValue(headers[i], record[i]);
				}
				
			}else {
				return null;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		System.out.println("Reading from CSV " +  output);
		return output;
	}

}
