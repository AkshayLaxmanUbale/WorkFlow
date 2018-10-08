package com.workflow.bean;

import java.util.HashMap;
import java.util.Map;

public class Node {
	private Map<String,Object> config= new HashMap<String,Object>();
	private Map<String,Object> input= new HashMap<String,Object>();
	private Map<String,Object> output= new HashMap<String,Object>();
	public Map<String, Object> getConfig() {
		return config;
	}
	public Map<String, Object> getInput() {
		return input;
	}
	public Map<String, Object> getOutput() {
		return output;
	}
	public void setConfig(Map<String, Object> config) {
		this.config = config;
	}
	public void setInput(Map<String, Object> input) {
		this.input = input;
	}
	public void setOutput(Map<String, Object> output) {
		this.output = output;
	}
	
}
