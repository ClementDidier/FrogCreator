package system;

import system.events.GameEvent;
import system.events.GameEventType;
import utils.FrogException;

public class HealthSystem extends AbstractSystem 
{
	public HealthSystem()
	{
		super(GameEventType.DAMAGES, GameEventType.HEAL);
	}

	@Override
	public void updateHandler(float delta) 
	{
		
	}

	@Override
	public void eventHandler(GameEvent event) throws FrogException
	{
		switch(event.getEventType())
		{
		case DAMAGES:
			// TODO : Something
			break;
		case HEAL:
			// TODO : Something
			break;
			default:
				throw new FrogException("Evenement non pris en charge atteint");
		}
	}
}
