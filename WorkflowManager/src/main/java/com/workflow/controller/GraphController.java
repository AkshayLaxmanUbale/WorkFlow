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
	public boolean saveWorkflow(@RequestBody JSONObject jgraph) {
		graphService.saveGraph(jgraph);
		return true;
	}
	
	@RequestMapping(value="/create", method=RequestMethod.POST, headers = "Accept=application/json")
	public boolean createWorkflow(@RequestBody String name) {
		return graphService.newWorkflow(name);
	}
	
	@RequestMapping(value="/open", method=RequestMethod.POST, headers = "Accept=application/json")
	public JSONObject getWorkflow(@RequestBody String name) {
		return graphService.getWorkflow(name);
	}
}
