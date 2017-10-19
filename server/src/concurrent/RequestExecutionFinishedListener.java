package concurrent;

import net.RequestResult;

public interface RequestExecutionFinishedListener 
{
	void requestExecutionFinished(RequestResult result);
}
