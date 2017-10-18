package com.frog.net;

public enum RequestType 
{
	NONE((byte)0),
	CONNECT((byte)1),
	DISCONNECT((byte)2);
	
	private Byte b;
	
	RequestType(Byte b)
	{
		this.b = b;
	}
	
	public Byte getByte()
	{
		return this.b;
	}
}
