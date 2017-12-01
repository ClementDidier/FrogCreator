package application;
import java.io.IOException;
import java.net.UnknownHostException;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import game.net.GameNetwork;
import net.IPacketListener;
import net.Packet;
import net.PacketBalancer;
import net.PacketType;
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
		
		GameNetwork network = new GameNetwork();
		PacketBalancer balancer = new PacketBalancer();
		balancer.subscribe(PacketType.CONNECT_RESULT, new IPacketListener() 
		{
			public void receivePacket(Packet packet) 
			{
				System.out.println("Connecté au serveur de jeu !\nTOKEN : " + network.getToken());
			}
		});
		
		network.addPacketBalancer(balancer);
		network.start("127.0.0.1", 5000);
		network.connect("account", "password");
	}
}
