package concurrent;

import net.Packet;

public interface RequestListener 
{
	void onRequestExecutionFinished(Packet result);
}
