package net;

import java.io.UnsupportedEncodingException;

import org.json.JSONObject;

import utils.FrogException;

public class Packet 
{
	protected static final String ENCODING = "UTF-8";
	protected static final String PACKET_TYPE_KEY = "PacketType";
	protected static final String PACKET_OBJECT_KEY = "PacketObject";
	
	private byte[] data;
	private PacketType packetType;
	private String jsonObject;
	private String finalObject;
	
	private Packet()
	{
		// nothing
	}
	
	public Packet(PacketType packetType, String jsonObject) throws FrogException
	{
		this.packetType = packetType;
		this.jsonObject = jsonObject;
		
		JSONObject obj = new JSONObject();
		obj.put(PACKET_TYPE_KEY, this.packetType.ordinal());
		obj.put(PACKET_OBJECT_KEY, this.jsonObject);
		this.finalObject = obj.toString();
		
		try {
			this.data = this.finalObject.getBytes(ENCODING);
		} catch (UnsupportedEncodingException e) {
			throw new FrogException("Erreur lors de la tentative de transformation des données");
		}
	}
	
	public PacketType getPacketType()
	{
		return this.packetType;
	}
	
	public String getJsonObject()
	{
		return this.jsonObject;
	}
	
	public byte[] getData()
	{
		return this.data;
	}
	
	public static Packet getPacket(byte[] data) throws FrogException
	{
		Packet p = new Packet();
		p.data = data;
		try 
		{
			p.finalObject = new String(data, ENCODING);
			JSONObject obj = new JSONObject(p.finalObject);
			p.packetType = PacketType.values()[obj.getInt(PACKET_TYPE_KEY)];
			p.jsonObject = obj.getString(PACKET_OBJECT_KEY);
		} 
		catch (Exception e) 
		{
			throw new FrogException("Impossible de convertir les données reçues");
		}
		
		return p;
	}
	
	public String toString()
	{
		return this.finalObject;
	}
}