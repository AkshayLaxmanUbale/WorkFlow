package com.predii.component;

public interface Component {
	public boolean preProcess(Entity config);
	public Entity process(Entity input);
}
