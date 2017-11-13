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
		config.width = 800;
		config.height = 480;
		GameClient client = new GameClient();
		new LwjglApplication(client, config);
	}
}
