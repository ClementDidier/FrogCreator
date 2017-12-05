package net;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import utils.FrogException;

/**
 * <h1>Aiguilleur de packets internet de jeu</h1>
 * <p>Classe permettant à des entités de souscrire à des notification. 
 * Ces notification sont réalisées pour chaque reception de packets de
 * jeu et sont distribuées spécifiquement et de façon optimisé au entitées
 * ayant demandé de les recevoirs.</p>
 */
public class PacketSubscriber
{
	private HashMap<PacketType, ArrayList<IPacketListener>> listeners;
	
	public PacketSubscriber()
	{
		this.listeners = new HashMap<PacketType, ArrayList<IPacketListener>>();
	}
	
	public synchronized void subscribe(PacketType type, IPacketListener packetHandler) throws FrogException
	{
		if(!this.listeners.containsKey(type))
			this.listeners.put(type, new ArrayList<IPacketListener>());
		
		this.listeners.get(type).add(packetHandler);
	}
	
	public synchronized void unsubscribe(PacketType type, IPacketListener packetHandler)
	{
		if(this.listeners.containsKey(type))
		{
			List<IPacketListener> list = this.listeners.get(type);
			list.remove(packetHandler);
			if(list.isEmpty())
				this.listeners.remove(type);
		}
	}
	
	public synchronized void pushPacket(Packet packet)
	{
		if(this.listeners.containsKey(packet.getType()))
		{
			List<IPacketListener> list = this.listeners.get(packet.getType());
			int size = list.size();
			for(int i = 0; i < size; i++)
			{
				list.get(i).onPacketReceived(packet);
			}
		}
	}
}
