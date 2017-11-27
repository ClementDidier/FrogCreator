package program;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import concurrent.ClientWorker;
import concurrent.RequestManager;
import plugin.FrogPlugin;
import plugin.Plugin;
import plugin.PluginLoader;

public class Program 
{
	private final static int MAX_THREAD = 10;
	private final static int PORT = 5000;
	
	private final static String PLUGINS_FOLDER = "plugins";
	private final static Class<FrogPlugin> PLUGIN_ANNOTATION_CLASS = FrogPlugin.class;

	private static boolean stopped = false;
	private static boolean verbose = true;
	
	public static void main(String[] args)
	{
		Program.log("Chargement des plugins...");
		
		PluginLoader<Plugin> pluginLoader = new PluginLoader<Plugin>(PLUGINS_FOLDER, PLUGIN_ANNOTATION_CLASS);
		List<Plugin> plugins = pluginLoader.getPlugins();	
		
		for(Plugin plugin : plugins)
		{
			try
			{
				Program.log("================ [Plugin] " + plugin.getName() + " ================\n" +
							"Auteur  : " + plugin.getAuthor() + "\n" +
							"Version : " + plugin.getVersion());
				plugin.load();
			}
			catch(Exception e)
			{
				// Suppression du mauvais plugin de la liste
				plugins.remove(plugin);
				if(verbose)
					e.printStackTrace();
			}
		}
		
		// Pool de threads client
		ExecutorService threadPool = Executors.newFixedThreadPool(MAX_THREAD);
		
		Program.log("Création du gestionnaire de requêtes...");
		// Création du gestionnaire de requêtes
		RequestManager requestManager = new RequestManager();
		
		// Serveur d'écoute
		Program.log("Lancement du serveur d'écoute sur le port " + PORT);
		try (ServerSocket server = new ServerSocket(PORT))
		{
			
			Program.log("En attente de clients...");
			
			while(!Program.isStopped())
			{
				// Attente d'une connexion entrante
				Socket clientSocket = server.accept();
				
				System.out.println("Nouveau client " + clientSocket.getInetAddress().getHostAddress());
				
				// Ajout du client stub dans la pool
				threadPool.submit(new ClientWorker(clientSocket, requestManager));
			}
			
			// Force tous les threads client à s'arrêter
			threadPool.shutdownNow();
			
			for(Plugin plugin : plugins)
				plugin.unload();
		} 
		catch(BindException e)
		{
			System.err.println("Impossible de lancer le serveur, le port est actuellement utilisé");
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Obtient l'état du serveur
	 * @return True dans le cas où le serveur est arrêté, False dans le cas contraire 
	 */
	public static synchronized boolean isStopped()
	{
		return stopped;
	}
	
	/**
	 * Affiche un message dans la sortie standard, si l'obtion verbeuse est activée
	 * @param message Le message à afficher dans la sortie
	 */
	public static void log(String message)
	{
		if(verbose)
			System.out.println(message);
	}
}
