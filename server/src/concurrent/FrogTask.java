package concurrent;

import com.frog.net.request.NetRequest;

public class FrogTask
{
	private NetRequest request;
	private RequestExecutionFinishedListener callback;
	
	public FrogTask(NetRequest request, RequestExecutionFinishedListener callback)
	{
		this.request = request;
		this.callback = callback;
	}
	
	public NetRequest getRequest()
	{
		return this.request;
	}
	
	public RequestExecutionFinishedListener getCallback()
	{
		return this.callback;
	}
}
