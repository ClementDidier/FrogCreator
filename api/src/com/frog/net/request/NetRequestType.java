package com.frog.net.request;

public enum NetRequestType 
{
	NONE((byte)0),
	CONNECT((byte)1),
	DISCONNECT((byte)2);
	
	private Byte b;
	
	NetRequestType(Byte b)
	{
		this.b = b;
	}
	
	public Byte getByte()
	{
		return this.b;
	}
}
