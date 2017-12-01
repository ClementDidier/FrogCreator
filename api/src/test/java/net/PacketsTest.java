package net;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;

import org.json.JSONObject;
import org.junit.Test;

import utils.FrogException;

public class PacketsTest 
{
	@Test
	public void newPacketTest() throws UnsupportedEncodingException, FrogException
	{
		JSONObject obj = new JSONObject();
		obj.put("test", "value");
		Packet p = new Packet(PacketType.SYNC, obj.toString());
		assertEquals("JSONObjects non égaux", obj.toString(), p.getSerializedObject());
		assertEquals("PacketTypes non égaux", PacketType.SYNC, p.getType());
		
		JSONObject obj2 = new JSONObject();
		obj2.put(Packet.PACKET_TYPE_KEY, PacketType.SYNC.ordinal());
		obj2.put(Packet.PACKET_OBJECT_KEY, obj.toString());
		
		assertEquals("Packets non égaux", obj2.toString(), p.toJSON());
	}
	
	@Test
	public void getPacketTest() throws FrogException
	{
		JSONObject obj = new JSONObject();
		obj.append("test", "value");
		Packet p1 = new Packet(PacketType.SYNC, obj.toString());
		
		Packet p2 = Packet.getPacket(p1.toJSON());
		assertEquals("JSONObjects non égaux", p1.getSerializedObject(), p2.getSerializedObject());
		assertEquals("PacketTypes non égaux", p1.getType(), p2.getType());
		assertEquals("Resultats non égaux", p1.toJSON(), p2.toJSON());
	}
}
