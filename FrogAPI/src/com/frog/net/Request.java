package com.frog.net;

public class Request 
{
	private RequestType type;
	
	public Request()
	{
		this.type = RequestType.NONE;
	}
	
	public Request(RequestType type)
	{
		this.type = type;
	}
	
	public RequestType getType()
	{
		return this.type;
	}
}
