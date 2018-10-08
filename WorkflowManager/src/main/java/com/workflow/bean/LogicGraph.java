package com.workflow.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.workflow.bean.Node;

@Document(collection="logicGraph")
public class LogicGraph {

	@Id
	private String id;
	private List<Node> nodes= new ArrayList<Node>();
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<Node> getNodes() {
		return nodes;
	}
	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}
	public Map<String, String> getInput(int ind) {
		return nodes.get(ind).getInput();
	}
	public Map<String, String> getOutput(int ind) {
		return nodes.get(ind).getOutput();
	}
	public Map<String, String> getConfig(int ind) {
		return nodes.get(ind).getConfig();
	}
}
