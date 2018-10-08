package com.workflow.controller;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.workflow.service.GraphService;


@RestController
public class GraphController {

	@Autowired
	GraphService graphService;
	
	@RequestMapping(value="/save", method=RequestMethod.POST, headers = "Accept=application/json")
	public boolean addGraph(@RequestBody JSONObject jgraph) {
		graphService.extract(jgraph);
		graphService.saveGraph(jgraph);
		return true;
	}
}
