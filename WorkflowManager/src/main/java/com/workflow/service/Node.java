package com.workflow.service;

import java.util.HashMap;
import java.util.Map;

public class Node {
	private Map<String,String> config= new HashMap<String,String>();
	private Map<String,String> input= new HashMap<String,String>();
	private Map<String,String> output= new HashMap<String,String>();
	public Map<String, String> getConfig() {
		return config;
	}
	public Map<String, String> getInput() {
		return input;
	}
	public Map<String, String> getOutput() {
		return output;
	}
	public void setConfig(Map<String, String> config) {
		this.config = config;
	}
	public void setInput(Map<String, String> input) {
		this.input = input;
	}
	public void setOutput(Map<String, String> output) {
		this.output = output;
	}
	
}
