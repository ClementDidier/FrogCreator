package concurrent;

import com.frog.net.request.NetRequestResult;

public interface RequestExecutionFinishedListener 
{
	void requestExecutionFinished(NetRequestResult result);
}
