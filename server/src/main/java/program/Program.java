package program;
import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import concurrent.ClientWorker;
import concurrent.RequestManager;
import net.IClientListener;
import net.IServerListener;
import net.socket.FrogServerSocket;
import plugin.FrogPlugin;
import plugin.Plugin;
import plugin.PluginLoader;

public class Program 
{
	private final static int MAX_THREAD = 10;
	private final static int PORT = 5000;
	
	private final static String PLUGINS_FOLDER = "plugins";
	private final static Class<FrogPlugin> PLUGIN_ANNOTATION_CLASS = FrogPlugin.class;
	
	public static void main(String[] args)
	{
		PluginLoader<Plugin> pluginLoader = new PluginLoader<Plugin>(PLUGINS_FOLDER, PLUGIN_ANNOTATION_CLASS);
		List<Plugin> plugins = pluginLoader.getPlugins();
		
		// Pool de threads client
		ExecutorService threadPool = Executors.newFixedThreadPool(MAX_THREAD);
		
		loadPlugins(plugins);
		startServerLoop(threadPool);
		
		stopServer(threadPool);
		unloadPlugins(plugins);
	}
	
	private static void loadPlugins(List<Plugin> plugins)
	{
		System.out.println("Chargement des plugins...");
		
		for(Plugin plugin : plugins)
		{
			try
			{
				System.out.println("[Plugin : " + plugin.getName() + "] Auteur  : " + plugin.getAuthor() + "; Version : " + plugin.getVersion());
				plugin.load();
			}
			catch(Exception e)
			{
				// Suppression du mauvais plugin de la liste
				plugins.remove(plugin);
				e.printStackTrace();
			}
		}
	}
	
	private static void unloadPlugins(List<Plugin> plugins)
	{
		// Décharge les plugins
		for(Plugin plugin : plugins)
			plugin.unload();
	}
	
	private static void startServerLoop(ExecutorService threadPool)
	{
		FrogServerSocket server = new FrogServerSocket();
		
		System.out.println("Création du gestionnaire de requêtes...");
		// Création du gestionnaire de requêtes
		RequestManager requestManager = new RequestManager(server);
		
		server.setServerListener(new IServerListener() 
		{
			public void onStartUp(FrogServerSocket server) 
			{
				// Lancement du request Manager au démarrage du serveur
				requestManager.start();
			}
		});
		server.setClientListener(new IClientListener() 
		{
			public void onClientAccept(Socket client) 
			{
				System.out.println("Nouveau client " + client.getInetAddress().getHostAddress());
				
				// Ajout du client stub dans la pool
				try {
					threadPool.submit(new ClientWorker(server, client, requestManager));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		
		// Serveur d'écoute
		System.out.println("Lancement du serveur d'écoute sur le port " + PORT);
		server.start(PORT);
		
	}
	
	private static void stopServer(ExecutorService threadPool)
	{
		// Force tous les threads client à s'arrêter
		threadPool.shutdownNow();
	}
}
