package system.components;

import entities.Entity;
import entities.IUpdatable;

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
