package concurrent;

import com.frog.net.RequestResult;

public interface RequestExecutionFinishedListener 
{
	void requestExecutionFinished(RequestResult result);
}
