package com.frog.net.request;

import com.frog.net.DataPacket;

public class NetRequest 
{
	private NetRequestType type;
	
	protected DataPacket data;
	
	public NetRequest()
	{
		this.type = NetRequestType.NONE;
		this.data = new DataPacket();
		this.data.write(this.type.getByte());
	}
	
	public NetRequest(NetRequestType type)
	{
		this.type = type;
		this.data = new DataPacket();
		this.data.write(this.type.getByte());
	}
	
	public NetRequestType getType()
	{
		return this.type;
	}
	
	public DataPacket getData()
	{
		return data;
	}
}
