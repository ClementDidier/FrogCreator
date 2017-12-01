package game.net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import net.IPacketListener;
import net.Packet;
import net.PacketBalancer;
import net.PacketType;
import utils.FrogException;

/**
 * <h1>Gestionnaire de packets internet de jeu</h1>
 * <p>Classe permettant de gérer la communication avec le serveur de jeu</p>
 */
public class GameNetwork implements IPacketListener
{
	private static final int DURATION_BETWEEN_TENTATIVES = 1000;
	private static final int TENTATIVES = 10;
	
	private List<PacketBalancer> balancers;
	private Socket socket;
	private String token;
	private boolean isRunning;
	
	protected BufferedReader reader;
	protected PrintWriter writer;
	
	public GameNetwork()
	{
		this.balancers = new ArrayList<PacketBalancer>();
		this.isRunning = false;
	}
	
	/**
	 * <h1>Ajoute un nouveau aiguilleur de packets</h1>
	 * <p>Les souscripteurs de l'aiguilleur recevront les packets en reception</p>
	 * @param balancer Le nouveau aiguilleur à ajouter
	 */
	public synchronized void addPacketBalancer(PacketBalancer balancer)
	{
		this.balancers.add(balancer);
	}
	
	/**
	 * <h1>Supprime un aiguilleur de packets</h1>
	 * <p>Les souscripteurs de l'aiguilleur ne recevront plus les packets en reception</p>
	 * @param balancer L'aiguilleur à supprimer
	 */
	public synchronized void removePacketBalancer(PacketBalancer balancer)
	{
		this.balancers.remove(balancer);
	}
	
	/**
	 * Démarre la communication avec le serveur de jeu si possible
	 * @param ip L'adresse du serveur hôte
	 * @param port Le port d'écoute du serveur hôte
	 * @throws FrogException
	 */
	public void start(String ip, int port) throws FrogException
	{
		if(this.isRunning())
			return;
		
		if(this.trySocketConnect(ip, port, TENTATIVES, DURATION_BETWEEN_TENTATIVES))
		{
			this.isRunning = true;
			PacketReaderWorker worker = new PacketReaderWorker(this);
			worker.addListener(this);
			worker.start(); // Lancement du thread de lecture des packets en reception
		}
	}
	
	@Override
	public void receivePacket(Packet packet) 
	{
		if(this.token == null && packet.getType() == PacketType.CONNECT_RESULT)
		{
			JSONObject obj = new JSONObject(packet.getSerializedObject());
			boolean result = obj.getBoolean("result");
			if(result)
				this.token = obj.getString("token");
		}
		
		this.raiseEventToBalancers(packet);
		
		// do something with received packet...
	}

	/**
	 * Obtient l'état de la communication avec le serveur de jeu
	 * @category Couche serveur de jeu
	 * @return True si la communication est active, False dans le cas contraire
	 */
	public synchronized boolean isRunning()
	{
		return this.isRunning;
	}
	
	/**
	 * Défini l'arrêt de la communication avec le serveur de jeu
	 */
	public synchronized void stop()
	{
		this.isRunning = false;
	}
	
	/**
	 * <h1>Tentative de connection avec l'hôte distant</h1>
	 * <p>Tente une connexion avec l'hôte distant spécifié
	 * Si la connexion échoue, une nouvelle tentative est relancé au bout d'un temps défini et celà
	 * un nombre de fois défini également.</p>
	 * @param ip L'IP du serveur hôte
	 * @param port Le PORT d'écoute du serveur hôte
	 * @param tentativesCount Le nombre de tentatives de connexion maximum
	 * @param milisecondsBetweenEachTentatives Le temps d'attente entre chaque tentative en milisecondes
	 * @return True si la connexion a été réalisée avec succès, False dans le cas contraire
	 * @throws FrogException Jetée lorsqu'une erreur système survient
	 */
	private boolean trySocketConnect(String ip, int port, int tentativesCount, int milisecondsBetweenEachTentatives) throws FrogException
	{
		boolean result = false;
		int tentatives = 0;
		
		try
		{
			while(!result && tentatives <= tentativesCount)
			{
				try
				{
					this.socket = new Socket(ip, port);
					result = true;
				} 
				catch (Exception e) 
				{
					System.err.println(e.getMessage());
					result = false;
					tentatives++;
					Thread.sleep(milisecondsBetweenEachTentatives);
				}
			}
			
			if(result)
			{
				this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				this.writer = new PrintWriter(this.socket.getOutputStream(), true);
			}
		}
		catch(Exception e)
		{
			throw new FrogException(e.getMessage());
		}
		
		return result;
	}

	/**
	 * <h1>Tentative de connexion au serveur de jeu</h1>
	 * <p>Tente une connexion au serveur de jeu avec les informations d'authentification spécifiées.</p>
	 * @param account Le nom de compte joueur
	 * @param password Le mot de passe du compte joueur
	 * @throws FrogException
	 */
	public void connect(String account, String password) throws FrogException
	{
		if(!this.socket.isConnected())
			throw new FrogException("Tentative de connection au serveur de jeu sur un channel de communication non établi");
		
		System.out.println("Tentative de connexion au serveur de jeu...");
		Packet connectPacket = new Packet(PacketType.CONNECT, "Nothing");
		writer.println(connectPacket.toJSON());
	}
	
	/**
	 * Obtient l'état de la communication avec l'hôte
	 * @category Couche socket
	 * @return True si la communication est active, False dans le cas contraire
	 */
	public synchronized boolean isConnected()
	{
		if(this.socket != null)
			return this.socket.isConnected();
		return false;
	}
	
	/**
	 * Obtient le token de communication avec le serveur de jeu résultant de l'authentification
	 * @return Null si l'authentification n'a pas été réalisée, ou a échouée, une clé dans le cas contraire
	 */
	public String getToken() 
	{
		return token;
	}
	
	private void raiseEventToBalancers(Packet packet)
	{
		for(int i = 0; i < this.balancers.size(); i++)
		{
			this.balancers.get(i).pushPacket(packet);
		}
	}
}
