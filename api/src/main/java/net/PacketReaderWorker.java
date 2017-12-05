package net;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.socket.FrogClientSocket;
import utils.FrogException;

public class PacketReaderWorker extends Thread
{
	private List<IPacketListener> listeners;
	private FrogClientSocket network;
	
	public PacketReaderWorker(FrogClientSocket network)
	{
		this.listeners = new ArrayList<IPacketListener>();
		this.network = network;
	}
	
	public void run()
	{
		while(this.network.isRunning() && this.network.isConnected())
		{
			try 
			{
				System.out.println("Waiting for packets...");
				String line = this.network.getReader().readLine();
				System.out.println("Packet received : " + line);
				Packet responsePacket = Packet.getPacket(line);
				raiseReceivedPacketEvent(responsePacket);
			} 
			catch (IOException | FrogException e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	private void raiseReceivedPacketEvent(Packet packet)
	{
		for(int i = 0; i < this.listeners.size(); i++)
		{
			this.listeners.get(i).onPacketReceived(packet);
		}
	}
	
	/**
	 * <h1>Ajoute un nouveau souscripteur</h1>
	 * <p>Le nouveau souscripteur sera notifié à chaque nouveau packet</p>
	 * @param listener Le nouveau souscripteur à l'écoute des évènements
	 */
	public synchronized void addListener(IPacketListener listener)
	{
		this.listeners.add(listener);
	}
	
	/**
	 * <h1>Supprime un souscripteur</h1>
	 * <p>Le souscripteur ne recevra plus de notification d'évènements</p>
	 * @param listener Le souscripteur à l'écoute des évènements
	 */
	public synchronized void removeListener(IPacketListener listener)
	{
		this.listeners.remove(listener);
	}
}
