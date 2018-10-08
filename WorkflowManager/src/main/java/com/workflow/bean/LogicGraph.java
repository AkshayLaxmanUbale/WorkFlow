package com.workflow.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.workflow.bean.Node;

public class LogicGraph {

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
	public Map<String, Object> getInput(int ind) {
		return nodes.get(ind).getInput();
	}
	public Map<String, Object> getOutput(int ind) {
		return nodes.get(ind).getOutput();
	}
	public Map<String, Object> getConfig(int ind) {
		return nodes.get(ind).getConfig();
	}
}
