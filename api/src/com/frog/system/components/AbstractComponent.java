package com.frog.system.components;

import com.frog.entities.Entity;
import com.frog.entities.IUpdatable;

public abstract class AbstractComponent implements IUpdatable
{
	private Entity parent;
	
	public Entity getParent() {
		return parent;
	}

	public void setParent(Entity parent) {
		this.parent = parent;
	}
}
