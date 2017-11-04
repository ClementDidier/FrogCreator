package com.frog.system;

import java.util.EnumSet;
import java.util.Stack;

import com.frog.entities.IUpdatable;
import com.frog.log.Console;
import com.frog.system.events.GameEvent;
import com.frog.system.events.GameEventType;
import com.frog.utils.FrogException;

public abstract class AbstractSystem implements IUpdatable
{
	protected Stack<GameEvent> eventStack;
	protected EnumSet<GameEventType> acceptedEventsTypes;
	
	public AbstractSystem()
	{
		this.eventStack = new Stack<GameEvent>();
		this.acceptedEventsTypes = EnumSet.noneOf(GameEventType.class);
	}
	
	protected AbstractSystem(GameEventType...eventsTypes)
	{
		this();
		
		for(GameEventType type : eventsTypes)
			this.acceptedEventsTypes.add(type);
	}
	
	public void pushEvent(GameEvent event)
	{
		this.eventStack.push(event);
	}
	
	public boolean acceptEventType(GameEventType type)
	{
		return this.acceptedEventsTypes.contains(type);
	}
	
	@Override
	public void update(float delta)
	{
		updateHandler(delta);
		
		while(!this.eventStack.isEmpty())
		{
			try
			{
				this.eventHandler(this.eventStack.pop());
			}
			catch(FrogException e)
			{
				Console.log.error(e);
			}
		}
	}
	
	public abstract void updateHandler(float delta);
	
	public abstract void eventHandler(GameEvent event) throws FrogException;
}
