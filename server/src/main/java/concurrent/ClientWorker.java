package concurrent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import net.Packet;
import net.PacketType;
import program.Program;
import utils.FrogException;

public class ClientWorker implements Runnable, RequestExecutionFinishedListener
{
	private Socket socket;
	private RequestManager manager;
	private BufferedReader in;
	
	@SuppressWarnings("unused")
	private PrintWriter out;
	
	public ClientWorker(Socket socket, RequestManager manager) throws IOException
	{
		this.socket = socket;
		this.manager = manager;
		
		// Création des objets de communication
		this.out = new PrintWriter(socket.getOutputStream(), true);
		this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}
	
	@Override
	public void run() 
	{
		while(this.socket.isConnected() && !Program.isStopped())
		{
			// Logique cliente
			// TODO: Lecture et execution des requètes reçues
			try {
				String str = this.in.readLine();
				if(str == "1")
					this.manager.submit(new Packet(PacketType.CONNECT, "Nothing"), this); // TODO : Revoir
			} catch (IOException | FrogException e) {
				e.printStackTrace();
				break;
			}
			
		}
	}

	@Override
	public void requestExecutionFinished(Packet result) 
	{
		System.out.println("Resultat de la requete : " + result.toString());
	}
}
