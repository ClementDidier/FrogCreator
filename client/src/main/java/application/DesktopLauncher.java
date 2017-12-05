package application;
import java.io.IOException;
import java.net.UnknownHostException;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import net.IPacketListener;
import net.Packet;
import net.PacketSubscriber;
import net.PacketType;
import net.socket.FrogClientSocket;
import utils.FrogException;

public class DesktopLauncher
{
	public static void main(String[] args) throws UnknownHostException, IOException, FrogException
	{	
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Frog Creator - Client";
		config.width = 1280;
		config.height = 800;
		new LwjglApplication(GameClient.getInstance(), config); // new thread
		
		FrogClientSocket network = new FrogClientSocket();
		PacketSubscriber packetSubscribers = new PacketSubscriber();
		packetSubscribers.subscribe(PacketType.CONNECT_RESULT, new IPacketListener() 
		{
			public void onPacketReceived(Packet packet) 
			{
				System.out.println("Connecté au serveur de jeu !\nTOKEN : " + network.getToken());
			}
		});
		
		network.addPacketSubscribers(packetSubscribers);
		network.start("127.0.0.1", 5000);
		
		if(network.isRunning())
			network.connect("account", "password");
	}
}
