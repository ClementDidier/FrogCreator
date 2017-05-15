package com.frog.net;

public class Request 
{
	private RequestType type;
	
	public Request()
	{
		this.type = RequestType.NONE;
	}
	
	public RequestType getType()
	{
		return this.type;
	}
}
