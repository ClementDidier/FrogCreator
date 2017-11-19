package system;

import entities.Entity;
import system.components.HealthComponent;
import system.events.GameEvent;
import system.events.GameEventType;
import utils.FrogException;

public class HealthSystem extends AbstractSystem 
{
	public HealthSystem()
	{
		// Event types accepted
		super(GameEventType.DAMAGES, GameEventType.HEAL);
	}
	
	public void update(float delta)
	{
		// Something
		
		super.update(delta);
	}

	@Override
	public void eventReceived(GameEvent event) throws FrogException
	{
		switch(event.getEventType())
		{
			case DAMAGES:
				Object[] params = event.getParameters();
				Entity entity = (Entity) params[0];
				@SuppressWarnings("unused") Entity enemy = (Entity) params[1];
				Integer damages = (Integer) params[2];
				
				HealthComponent component = (HealthComponent) entity.getComponent(HealthComponent.COMPONENT_KEY);
				component.decreaseHealth(damages);
				break;
			case HEAL:
				// TODO : Something
				break;
				default:
					throw new FrogException("Evenement non pris en charge atteint");
		}
	}
}
