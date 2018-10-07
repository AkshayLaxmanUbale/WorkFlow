package com.workflow.service;

import java.util.ArrayList;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.workflow.bean.LogicGraph;

@Service("graphExtractService")
public class GraphExtract {
	
	@Autowired
	MongoTemplate mongoTemplate;
	final String COLLECTION="logicGraph";
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
		mongoTemplate.insert(lgraph, COLLECTION);
		return lgraph.getId();
	}
}
