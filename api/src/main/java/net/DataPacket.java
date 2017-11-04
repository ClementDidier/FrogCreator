package net;

import java.util.ArrayList;
import java.util.List;

public class DataPacket 
{
	private int index;
	private List<Byte> dump;
	
	public DataPacket()
	{
		this.dump = new ArrayList<Byte>();
		this.index = 0;
	}
	
	public DataPacket(byte[] buffer)
	{
		this();
		for(byte b : buffer)
		{
			this.dump.add(b);
		}
	}
	
	public byte readByte()
	{
		if(this.index == this.dump.size())
			throw new OutOfMemoryError("Tentative de lecture d'un octet en dehors du dump");
		
		return this.dump.get(index++).byteValue();
	}
	
	public byte[] readToEnd()
	{
		if(this.getCursorPosition() >= this.getDumpSize())
			throw new OutOfMemoryError("Tentative de lecture d'un octet en dehors du dump");
		
		byte[] result = new byte[this.getDumpSize() - this.getCursorPosition()];
		for(int i = this.getCursorPosition(), j = 0; i < this.getDumpSize(); i = this.getCursorPosition(), j++)
		{
			result[j] = this.readByte();
		}
		
		return result;
	}
	
	public char readChar()
	{
		return (char)this.readByte();
	}

	public boolean readBoolean()
	{
		byte b = this.readByte();
		
		return ((b & 1) == 1);
	}
	
	public short readShort()
	{
		byte b1 = this.readByte();
		byte b2 = this.readByte();
		
		return (short) (b1 << 8 | b2 & 0xFF);
	}
	
	public int readInt()
	{
		short s1 = this.readShort();
		short s2 = this.readShort();
		
		return (int) (s1 << 16 | s2 & 0xFFFF);
	}
	
	public long readLong()
	{
		Integer i1 = readInt();
		Integer i2 = readInt();
		
		return (long) (i1.longValue() << 32 | i2.longValue() & 0xFFFFFFFFL);
	}
	
	public float readFloat() 
	{
		return Float.intBitsToFloat(this.readInt());
	}
	
	public double readDouble()
	{
		return Double.longBitsToDouble(this.readLong());
	}
	
	public String readString()
	{
		StringBuilder sb = new StringBuilder();
		int stringSize = this.readInt();
		
		for(int i = 0; i < stringSize; i++)
		{
			sb.append(this.readChar());
		}
		
		return sb.toString();
	}
	
	public void write(byte b)
	{
		if(this.index >= this.dump.size())
			this.dump.add(b);
		else
			this.dump.set(this.index, b);
		this.index++;
	}
	
	public void write(byte[] bytes)
	{
		for(byte b : bytes)
		{
			this.write(b);
		}
	}
	
	public void write(char c)
	{
		this.write((byte)c);
	}
	
	public void write(boolean bool)
	{
		this.write((byte) (bool ? 0x1 : 0x0));
	}
	
	public void write(short s)
	{
		this.write((byte)(s >> 8));
		this.write((byte)(s & 0xFF));
	}
	
	public void write(int i)
	{
		this.write((short)(i >> 16));
		this.write((short)(i & 0xFFFF));
	}
	
	public void write(long l)
	{
		this.write((int)(l >> 32));
		this.write((int)(l & 0xFFFFFFFF));
	}
	
	public void write(float f)
	{
		this.write(Float.floatToIntBits(f));
	}
	
	public void write(double d) 
	{
		this.write(Double.doubleToLongBits(d));
	}
	
	public void write(String s) 
	{
		this.write(s.length());
		
		for(int i = 0; i < s.length(); i++)
		{
			this.write(s.charAt(i));
		}
	}
	
	public void resetPointer()
	{
		this.index = 0;
	}
	
	public void pointerToEnd()
	{
		this.index = this.dump.size();
	}
	
	public int getCursorPosition()
	{
		return this.index;
	}
	
	public int getDumpSize()
	{
		return this.dump.size();
	}
	
	@Override
	public String toString() 
	{
		StringBuilder sb = new StringBuilder(this.dump.size() * 2);
		for(Byte b : this.dump)
			sb.append(String.format("%02x ", b));
		return sb.toString().toUpperCase();
	}
}
