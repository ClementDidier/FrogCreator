package system.components;

import system.GameSystem;
import system.events.GameEvent;
import system.events.GameEventType;

public class HealthComponent extends AbstractComponent 
{
	public static final String COMPONENT_KEY = "HealthComponentKey";
	private int health;
	
	public HealthComponent(int maxHealth) 
	{
		this.setHealth(maxHealth);
	}
	
	@Override
	public void update(float delta) 
	{
		if(this.health == 0)
			GameSystem.instance.pushEvent(new GameEvent(GameEventType.DEATH, this.getParent()));
	}

	public int getHealth() 
	{
		return this.health;
	}

	public void setHealth(int health) 
	{
		this.health = health;
	}

	@Override
	public String getKey() {
		return COMPONENT_KEY;
	}
}
