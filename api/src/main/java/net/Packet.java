package net;

import org.json.JSONObject;

import system.objects.SerializableObject;
import utils.FrogException;

public class Packet implements SerializableObject
{
	protected static final String PACKET_TYPE_KEY = "PacketType";
	protected static final String PACKET_OBJECT_KEY = "PacketObject";
	
	private PacketType packetType;
	private String serialObject;
	
	private Packet()
	{
		// nothing
	}
	
	public Packet(PacketType packetType, String serialObject)
	{
		this.packetType = packetType;
		this.serialObject = serialObject;
	}
	
	public PacketType getType()
	{
		return this.packetType;
	}
	
	public String getSerializedObject()
	{
		return this.serialObject;
	}
	
	public static Packet getPacket(String jsonData) throws FrogException
	{
		Packet p = new Packet();
		try 
		{
			JSONObject obj = new JSONObject(jsonData);
			p.packetType = PacketType.values()[obj.getInt(PACKET_TYPE_KEY)];
			p.serialObject = obj.getString(PACKET_OBJECT_KEY);
		} 
		catch (Exception e) 
		{
			throw new FrogException("Impossible de convertir les données reçues");
		}
		
		return p;
	}

	@Override
	public String toJSON() 
	{
		JSONObject obj = new JSONObject();
		obj.put(PACKET_TYPE_KEY, this.packetType.ordinal());
		obj.put(PACKET_OBJECT_KEY, this.serialObject);
		return obj.toString();
	}
}