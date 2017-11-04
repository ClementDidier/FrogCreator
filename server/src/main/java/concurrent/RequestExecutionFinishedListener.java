package concurrent;

import net.request.NetRequestResult;

public interface RequestExecutionFinishedListener 
{
	void requestExecutionFinished(NetRequestResult result);
}
