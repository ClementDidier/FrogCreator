package com.frog.system.components;

import com.frog.system.GameSystem;
import com.frog.system.events.GameEvent;
import com.frog.system.events.GameEventType;

public class HealthComponent extends AbstractComponent 
{
	private int health;
	
	public HealthComponent(int maxHealth) 
	{
		this.setHealth(maxHealth);
	}
	
	@Override
	public void update(float delta) 
	{
		if(this.health == 0)
			GameSystem.INSTANCE.pushEvent(new GameEvent(GameEventType.DEATH, this.getParent()));
	}

	public int getHealth() 
	{
		return health;
	}

	public void setHealth(int health) 
	{
		this.health = health;
	}
}
