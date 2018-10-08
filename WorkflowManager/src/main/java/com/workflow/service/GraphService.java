package com.workflow.service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.workflow.bean.JsonGraph;
import com.workflow.bean.LogicGraph;
import com.workflow.bean.Node;

@Service("graphSService")
public class GraphService {
	
	@Autowired
	MongoTemplate mongoTemplate;
	final String COLLECTION="jsonGraph";
	public LogicGraph extract(String name) {
		Query query=new Query();
		query.addCriteria(Criteria.where("name").is(name));
		JSONObject jgraph=(mongoTemplate.findOne(query, JsonGraph.class, COLLECTION)).getJgraph();
		LogicGraph lgraph=new LogicGraph();
		ArrayList<Node> nodes=new ArrayList<Node>();
		lgraph.setId((String)jgraph.get("name"));
		JSONArray nodeArray=(JSONArray)jgraph.get("nodeDataArray");
		Node temp=new Node();
		for(int i=0;i<nodeArray.size();i++) {
			temp.setConfig((Map<String,Object>)(((JSONObject)nodeArray.get(i)).get("config")));
			temp.setInput((Map<String,Object>)(((JSONObject)nodeArray.get(i)).get("input")));
			temp.setOutput((Map<String,Object>)(((JSONObject)nodeArray.get(i)).get("output")));
			nodes.add(temp);
		}
		lgraph.setNodes(nodes);
		return lgraph;
	}
	public void saveGraph(JSONObject jgraph) {
		JsonGraph jsonGraph=new JsonGraph();
		jsonGraph.setName((String)jgraph.get("name"));
		jsonGraph.setJgraph(jgraph);
		mongoTemplate.insert(jsonGraph, COLLECTION);
	}
	public boolean newWorkflow(String name) {
		Query query=new Query();
		query.addCriteria(Criteria.where("name").is(name));
		if(mongoTemplate.find(query,JsonGraph.class,COLLECTION)!=null) {
			return false;
		}
		else {
			JsonGraph jgraph=new JsonGraph();
			JSONParser parser=new JSONParser();
			
			jgraph.setName(name);
			try {
				jgraph.setJgraph((JSONObject)(parser.parse(new FileReader("/WorkflowManager/dagInit.json"))));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			mongoTemplate.insert(jgraph, COLLECTION);
			return true;
		}
	}
	
	public JSONObject getWorkflow(String name) {
		Query query=new Query();
		query.addCriteria(Criteria.where("name").is(name));
		JsonGraph jgraph=(JsonGraph) mongoTemplate.find(query,JsonGraph.class,COLLECTION);
		return jgraph.getJgraph();
	}
}
