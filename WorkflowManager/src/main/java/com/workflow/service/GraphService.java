package com.workflow.service;

import java.util.ArrayList;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.workflow.bean.JsonGraph;
import com.workflow.bean.LogicGraph;

@Service("graphSService")
public class GraphService {
	
	@Autowired
	MongoTemplate mongoTemplate;
	final String L_COLLECTION="logicGraph";
	final String J_COLLECTION="jsonGraph";
	public String extract(JSONObject jgraph) {
		LogicGraph lgraph=new LogicGraph();
		ArrayList<Node> nodes=new ArrayList<Node>();
		lgraph.setId((String)jgraph.get("name"));
		JSONArray nodeArray=(JSONArray)jgraph.get("nodeDataArray");
		Node temp=new Node();
		for(int i=0;i<nodeArray.size();i++) {
			temp.setConfig((Map<String,String>)(((JSONObject)nodeArray.get(i)).get("config")));
			temp.setInput((Map<String,String>)(((JSONObject)nodeArray.get(i)).get("input")));
			temp.setOutput((Map<String,String>)(((JSONObject)nodeArray.get(i)).get("output")));
			nodes.add(temp);
		}
		lgraph.setNodes(nodes);
		mongoTemplate.insert(lgraph, L_COLLECTION);
		return lgraph.getId();
	}
	public void saveGraph(JSONObject jgraph) {
		JsonGraph jsonGraph=new JsonGraph();
		jsonGraph.setName((String)jgraph.get("name"));
		jsonGraph.setJgraph(jgraph);
		mongoTemplate.insert(jsonGraph, J_COLLECTION);
	}
}
