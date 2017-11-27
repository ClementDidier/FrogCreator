package application;
import java.io.IOException;
import java.net.UnknownHostException;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher
{
	public static void main(String[] args) throws UnknownHostException, IOException
	{
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Frog Creator - Client";
		config.width = 1280;
		config.height = 800;
		new LwjglApplication(GameClient.getInstance(), config); // new thread
	}
	
	// TODO : Create FrogSocket : try to reconnect each X seconds after disconnection
	/*private static void initialize() throws UnknownHostException, IOException
	{
		try
		(	Socket socket = new Socket("127.0.0.1", 5000);
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter writer = new PrintWriter(socket.getOutputStream(), true))
		{
			System.out.println("Connection...");
			Packet connectPacket = new Packet(PacketType.CONNECT, "Nothing");
			writer.println(connectPacket.toJSON());
			
			while(true)
			{
				try 
				{
					System.out.println(reader.readLine());
				} 
				catch(SocketException e) 
				{ 
					System.err.println("Disconnected from server...");
				}
			}
		}
	}*/
}
