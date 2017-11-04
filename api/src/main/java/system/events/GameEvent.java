package system.events;

public class GameEvent 
{
	private GameEventType eventType;
	private Object[] parameters;
	
	public GameEvent(GameEventType eventType, Object...parameters)
	{
		this.eventType = eventType;
		this.parameters = parameters;
	}

	public GameEventType getEventType() {
		return eventType;
	}

	public Object[] getParameters() {
		return parameters;
	}
}
