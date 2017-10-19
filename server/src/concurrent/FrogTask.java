package concurrent;

import net.Request;

public class FrogTask
{
	private Request request;
	private RequestExecutionFinishedListener callback;
	
	public FrogTask(Request request, RequestExecutionFinishedListener callback)
	{
		this.request = request;
		this.callback = callback;
	}
	
	public Request getRequest()
	{
		return this.request;
	}
	
	public RequestExecutionFinishedListener getCallback()
	{
		return this.callback;
	}
}
