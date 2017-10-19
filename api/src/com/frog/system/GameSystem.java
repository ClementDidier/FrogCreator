package system;

import java.util.ArrayList;
import java.util.List;

import event.GameEvent;
import system.AbstractSystem;
import utils.FrogException;

public class GameSystem 
{
	// TODO : Revoir
	public static final GameSystem INSTANCE = new GameSystem();
	
	private List<AbstractSystem> systems;
	
	public GameSystem()
	{
		this.systems = new ArrayList<AbstractSystem>();
	}

	public List<AbstractSystem> getSystems() 
	{
		return systems;
	}

	public void addSystem(AbstractSystem system) 
	{
		this.systems.add(system);
	}
	
	public void removeSystem(AbstractSystem system)
	{
		this.systems.remove(system);
	}
	
	public void pushEvent(GameEvent event)
	{
		for(AbstractSystem system : this.systems)
		{
			if(system.acceptEventType(event.getEventType()))
			{
				system.pushEvent(event);
			}
		}
	}
}
