package concurrent;

import net.Packet;

public interface RequestExecutionFinishedListener 
{
	void requestExecutionFinished(Packet result);
}
