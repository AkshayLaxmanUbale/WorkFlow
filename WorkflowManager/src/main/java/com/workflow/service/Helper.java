package com.workflow.service;

import com.workflow.component.*;

public class Helper {
	
	public Object getObjectByClassName(String classname) {
		Object object = null;
		try {
			Class<?> cls = Class.forName(classname);
			object = cls.newInstance();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return object;
	}
	
}
