package concurrent;

import net.Packet;

public class FrogTask
{
	private Packet packet;
	private RequestListener callback;
	
	public FrogTask(Packet packet, RequestListener callback)
	{
		this.packet = packet;
		this.callback = callback;
	}
	
	public Packet getPacket()
	{
		return this.packet;
	}
	
	public RequestListener getCallback()
	{
		return this.callback;
	}
}
