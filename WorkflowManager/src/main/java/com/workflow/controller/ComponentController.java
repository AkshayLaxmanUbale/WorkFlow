package com.workflow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.workflow.service.Helper;

@RestController
public class ComponentController {
	
	@Autowired
	Helper helper;
	
	@RequestMapping(value="/getConfig/{component_name}", method= RequestMethod.GET)
	public ResponseEntity<String> getConfig(@PathVariable("component_name") String componentName){
		
		String config = helper.getConfig(componentName);
		
		return new ResponseEntity<String>(config,HttpStatus.OK);
		
	}
	
}
