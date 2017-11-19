package concurrent;

import net.Packet;

public class FrogTask
{
	private Packet packet;
	private RequestExecutionFinishedListener callback;
	
	public FrogTask(Packet packet, RequestExecutionFinishedListener callback)
	{
		this.packet = packet;
		this.callback = callback;
	}
	
	public Packet getPacket()
	{
		return this.packet;
	}
	
	public RequestExecutionFinishedListener getCallback()
	{
		return this.callback;
	}
}
