package net;

import java.util.HashMap;

import utils.FrogException;

/**
 * <h1>Aiguilleur de packets internet de jeu</h1>
 * <p>Classe permettant à des entités de souscrire à des notification. 
 * Ces notification sont réalisées pour chaque reception de packets de
 * jeu et sont distribuées spécifiquement et de façon optimisé au entitées
 * ayant demandé de les recevoirs.</p>
 */
public class PacketBalancer 
{
	private HashMap<PacketType, IPacketListener> listeners;
	
	public PacketBalancer()
	{
		this.listeners = new HashMap<PacketType, IPacketListener>();
	}
	
	public synchronized void subscribe(PacketType type, IPacketListener packetHandler) throws FrogException
	{
		if(this.listeners.containsKey(type))
			throw new FrogException("Le packet balancer dispose déjà d'un objet inscrit pour cet évènement");
		this.listeners.put(type, packetHandler);
	}
	
	public synchronized void unsubscribe(PacketType type)
	{
		this.listeners.remove(type);
	}
	
	public synchronized void pushPacket(Packet packet)
	{
		if(this.listeners.containsKey(packet.getType()))
			this.listeners.get(packet.getType()).onPacketReceived(packet);
	}
}
