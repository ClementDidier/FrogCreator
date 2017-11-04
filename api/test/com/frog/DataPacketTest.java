package com.frog;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.frog.net.DataPacket;

public class DataPacketTest 
{
	@Test(expected=OutOfMemoryError.class)
	public void dataPacketOutOfMemoryReadByte()
	{
		DataPacket packet = new DataPacket();
		packet.readByte();
		fail("La fonction n'a pas remont�e l'exception attendue");
	}
	
	@Test
	public void dataPacketWrite()
	{
		int bitsCount = 0;
		DataPacket packet = new DataPacket();
		
		// byte
		packet.write(Byte.MIN_VALUE); bitsCount += Byte.SIZE;
		packet.write(Byte.MAX_VALUE); bitsCount += Byte.SIZE;
		
		// short
		packet.write(Short.MIN_VALUE); bitsCount += Short.SIZE;
		packet.write(Short.MAX_VALUE); bitsCount += Short.SIZE;
		
		// int
		packet.write(Integer.MIN_VALUE); bitsCount += Integer.SIZE;
		packet.write(Integer.MAX_VALUE); bitsCount += Integer.SIZE;
		
		// boolean
		packet.write(Boolean.FALSE); bitsCount += 8;
		packet.write(Boolean.TRUE);	 bitsCount += 8;
		
		// char
		packet.write('a'); bitsCount += 8;
		packet.write((char)47); bitsCount += 8;
		
		// long
		packet.write(Long.MIN_VALUE); bitsCount += Long.SIZE;
		packet.write(Long.MAX_VALUE); bitsCount += Long.SIZE;
		
		// float
		packet.write(Float.MIN_VALUE); bitsCount += Float.SIZE;
		packet.write(Float.MAX_VALUE); bitsCount += Float.SIZE;
		
		// float
		packet.write(Double.MIN_VALUE); bitsCount += Double.SIZE;
		packet.write(Double.MAX_VALUE); bitsCount += Double.SIZE;
		
		assertEquals("La taille du dump est incorrecte", packet.getDumpSize(), bitsCount / 8);
		
		System.out.println("DUMP : " + packet.toString().toUpperCase());
	}
	
	@Test
	public void dataPacketReadByte()
	{
		DataPacket packet = new DataPacket();
		packet.write(Byte.MIN_VALUE);
		packet.write(Byte.MAX_VALUE);
		packet.resetPointer();
		assertEquals("Valeur lue (byte) incorrecte", packet.readByte(), Byte.MIN_VALUE);
		assertEquals("Valeur lue (byte) incorrecte", packet.readByte(), Byte.MAX_VALUE);
	}
	
	@Test
	public void dataPacketReadChar()
	{
		DataPacket packet = new DataPacket();
		packet.write('a');
		packet.write('Z');
		packet.write((char)56);
		packet.resetPointer();
		assertEquals("Valeur lue (char) incorrecte", packet.readChar(), 'a');
		assertEquals("Valeur lue (char) incorrecte", packet.readChar(), 'Z');
		assertEquals("Valeur lue (char) incorrecte", packet.readChar(), (char)56);
	}
	
	@Test
	public void dataPacketReadBoolean()
	{
		DataPacket packet = new DataPacket();
		packet.write(true);
		packet.write(false);
		packet.resetPointer();
		assertEquals("Valeur lue (boolean) incorrecte", packet.readBoolean(), true);
		assertEquals("Valeur lue (boolean) incorrecte", packet.readBoolean(), false);
	}
	
	@Test
	public void dataPacketReadShort()
	{
		DataPacket packet = new DataPacket();
		packet.write(Short.MIN_VALUE);
		packet.write(Short.MAX_VALUE);
		packet.resetPointer();
		assertEquals("Valeur lue (short) incorrecte", packet.readShort(), Short.MIN_VALUE);
		assertEquals("Valeur lue (short) incorrecte", packet.readShort(), Short.MAX_VALUE);
	}
	
	@Test
	public void dataPacketReadInt()
	{
		DataPacket packet = new DataPacket();
		packet.write(Integer.MIN_VALUE);
		packet.write(Integer.MAX_VALUE);
		packet.resetPointer();
		assertEquals("Valeur lue (integer) incorrecte", Integer.MIN_VALUE, packet.readInt());
		assertEquals("Valeur lue (integer) incorrecte", Integer.MAX_VALUE, packet.readInt());
	}
	
	@Test
	public void dataPacketReadLong()
	{
		DataPacket packet = new DataPacket();
		packet.write(Long.MIN_VALUE);
		packet.write(Long.MAX_VALUE);
		packet.resetPointer();
		assertEquals("Valeur lue (long) incorrecte", Long.MIN_VALUE, packet.readLong());
		assertEquals("Valeur lue (long) incorrecte", Long.MAX_VALUE, packet.readLong());
	}
	
	@Test
	public void dataPacketReadFloat()
	{
		DataPacket packet = new DataPacket();
		packet.write(Float.MIN_VALUE);
		packet.write(Float.MAX_VALUE);
		packet.resetPointer();
		assertTrue("Valeur lue (float) incorrecte", Float.MIN_VALUE == packet.readFloat());
		assertTrue("Valeur lue (float) incorrecte", Float.MAX_VALUE == packet.readFloat());
	}
	
	@Test
	public void dataPacketReadDouble()
	{
		DataPacket packet = new DataPacket();
		packet.write(Double.MIN_VALUE);
		packet.write(Double.MAX_VALUE);
		packet.resetPointer();
		assertEquals("Valeur lue (double) incorrecte", Double.MIN_VALUE, packet.readDouble(), 0.0001);
		assertEquals("Valeur lue (double) incorrecte", Double.MAX_VALUE, packet.readDouble(), 0.0001);
	}
	
	@Test
	public void dataPacketReadString()
	{
		DataPacket packet = new DataPacket();
		
		String s1 = "Account";
		String s2 = "Password";
		String s3 = "azertyuiop^qsdfghjklmwxcvbn,;:!";
		
		packet.write(s1);
		packet.write(s2);
		packet.write(s3);
		
		packet.resetPointer();
		
		assertEquals("Valeur lue (string) incorrecte", s1, packet.readString());
		assertEquals("Valeur lue (string) incorrecte", s2, packet.readString());
		assertEquals("Valeur lue (string) incorrecte", s3, packet.readString());
	}
}
