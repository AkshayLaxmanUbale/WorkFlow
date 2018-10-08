package com.workflow.bean;

import org.json.simple.JSONObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="jsonGraph")
public class JsonGraph {

	@Id
	private String name;
	private JSONObject jgraph;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public JSONObject getJgraph() {
		return jgraph;
	}
	public void setJgraph(JSONObject jgraph) {
		this.jgraph = jgraph;
	}
}
