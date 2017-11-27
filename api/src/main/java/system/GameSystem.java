package system;

import java.util.ArrayList;
import java.util.List;

import log.Console;
import system.events.GameEvent;
import system.events.GameEventListener;
import utils.FrogException;

public class GameSystem 
{
	// TODO : Revoir
	public static final GameSystem instance = new GameSystem();
	
	private List<AbstractSystem> systems;
	
	/**
	 * Liste des souscripteurs externes en écoute d'évènements entrants
	 */
	private List<GameEventListener> listeners;
	
	private GameSystem()
	{
		this.systems = new ArrayList<AbstractSystem>();
		this.listeners = new ArrayList<GameEventListener>();
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
	
	public void addEventListener(GameEventListener listener) 
	{
		this.listeners.add(listener);
	}
	
	public void removeEventListener(GameEventListener listener)
	{
		this.listeners.remove(listener);
	}
	
	/**
	 * Ajoute un nouvel évènement dans le système
	 * @param event Le nouvel évènement à prendre en charge
	 */
	public void pushEvent(GameEvent event)
	{
		for(AbstractSystem system : this.systems)
		{
			if(system.isAcceptedEventType(event.getEventType()))
			{
				system.pushEvent(event);
			}
		}
		
		/**
		 * Appel tous les souscripteurs en écoute d'évènements entrants
		 */
		for(GameEventListener listener : this.listeners)
		{
			try {
				listener.eventReceived(event);
			} catch (FrogException e) {
				Console.log.error(e);
			}
		}
	}
}
