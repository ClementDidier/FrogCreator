package net.socket;

import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

import net.IClientListener;
import net.IServerListener;

public class FrogServerSocket 
{
	public static final String PROTOCOL_VERSION = "v0.0.0.1";
	
	private boolean isRunning;
	private IClientListener clientListener;
	private IServerListener serverListener;
	
	public FrogServerSocket()
	{
		this.isRunning = false;
	}
	
	public void start(int port)
	{
		try (ServerSocket server = new ServerSocket(port))
		{
			this.isRunning = true;
			this.raiseServerStartUpEvent();
			
			while(this.isRunning())
			{
				// Attente d'une connexion entrante
				Socket clientSocket = server.accept();
				
				this.raiseClientConnectedEvent(clientSocket);
			}
		}
		catch(BindException ex)
		{
			System.err.println("Impossible de lancer le serveur, le port est actuellement utilisé");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			this.isRunning = false;
		}
	}

	/**
	 * Obtient l'état du serveur
	 * @category Couche serveur de jeu
	 * @return True si le serveur est actif, False dans le cas contraire
	 */
	public synchronized boolean isRunning()
	{
		return this.isRunning;
	}
	
	/**
	 * <h1>Met à jour l'entité en écoute de nouveaux clients</h1>
	 * <p>Le souscripteur recevra des notifications à chaque nouvelle connexion cliente</p>
	 * @param listener Le nouveau souscripteur
	 */
	public void setClientListener(IClientListener listener)
	{
		this.clientListener = listener;
	}
	
	/**
	 * <h1>Met à jour l'entité en écoute du cycle de vie du serveur</h1>
	 * <p>Le souscripteur recevra des notifications à chaque évènement du cycle du serveur</p>
	 * @param listener Le nouveau souscripteur
	 */
	public void setServerListener(IServerListener listener)
	{
		this.serverListener = listener;
	}
	
	private void raiseServerStartUpEvent()
	{
		if(this.serverListener != null)
			this.serverListener.onStartUp(this);
	}
	
	private void raiseClientConnectedEvent(Socket client)
	{
		if(this.clientListener != null)
			this.clientListener.onClientAccept(client);
	}
}
