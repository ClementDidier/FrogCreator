package component;

import common.IUpdatable;
import common.data.Entity;

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
