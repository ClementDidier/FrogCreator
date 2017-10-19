package net;

public class Request 
{
	private RequestType type;
	
	protected DataPacket data;
	
	public Request()
	{
		this.type = RequestType.NONE;
		this.data = new DataPacket();
		this.data.write(this.type.getByte());
	}
	
	public Request(RequestType type)
	{
		this.type = type;
		this.data = new DataPacket();
		this.data.write(this.type.getByte());
	}
	
	public RequestType getType()
	{
		return this.type;
	}
	
	public DataPacket getData()
	{
		return data;
	}
}
