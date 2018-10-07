package com.workflow.component;

public interface Component {
	public boolean init(Entity config);
	public Entity process(Entity input);
}
