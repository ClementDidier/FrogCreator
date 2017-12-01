package concurrent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

import net.Packet;
import program.Program;
import utils.FrogException;

public class ClientWorker implements Runnable, RequestListener
{
	private Socket socket;
	private RequestManager manager;
	private BufferedReader in;
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
		while(this.socket.isConnected() && !this.socket.isClosed() && !Program.isStopped())
		{
			// Logique cliente
			// TODO: Lecture et execution des requêtes reçues
			try 
			{
				String str = this.in.readLine();
				this.manager.submit(Packet.getPacket(str), this); // TODO : Revoir
			} 
			catch(SocketException ex)
			{
				// Socket closed
				System.out.println(String.format("Client %s exited", this.socket.getInetAddress()));
				try { this.socket.close(); } catch (IOException e) { e.printStackTrace(); break; }
			} 
			catch (FrogException e) 
			{
				e.printStackTrace();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
				break;
			}
			
		}
	}

	@Override
	public void onRequestExecutionFinished(Packet result) 
	{
		System.out.println("Resultat de la requete : " + result.toString());
		this.out.println(result.toJSON());
	}
}
