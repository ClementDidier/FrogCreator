package system;

import java.util.EnumSet;
import java.util.Stack;

import log.Console;
import system.events.GameEvent;
import system.events.GameEventListener;
import system.events.GameEventType;
import system.objects.UpdatableObject;
import utils.FrogException;

public abstract class AbstractSystem implements UpdatableObject, GameEventListener
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
		while(!this.eventStack.isEmpty())
		{
			try
			{
				this.eventReceived(this.eventStack.pop());
			}
			catch(FrogException e)
			{
				Console.log.error(e);
			}
		}
	}
}
