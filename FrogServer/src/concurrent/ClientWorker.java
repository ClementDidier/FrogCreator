package concurrent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.frog.net.Request;
import com.frog.net.RequestResult;

import program.Program;

public class ClientWorker implements Runnable, RequestExecutionFinishedListener
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
		while(this.socket.isConnected() && !Program.isStopped())
		{
			// Logique cliente
			// TODO: Lecture et execution des requètes reçues
			this.manager.submit(new Request(), this);
		}
	}

	@Override
	public void requestExecutionFinished(RequestResult result) 
	{
		System.out.println("Resultat de la requete : " + result.toString());
	}
}
