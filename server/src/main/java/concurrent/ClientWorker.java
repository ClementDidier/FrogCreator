package concurrent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

import org.json.JSONObject;

import net.Packet;
import net.PacketType;
import net.socket.FrogServerSocket;
import utils.FrogException;

public class ClientWorker implements Runnable, RequestListener
{
	private FrogServerSocket server;
	private Socket socket;
	private RequestManager manager;
	private BufferedReader in;
	private PrintWriter out;
	
	public ClientWorker(FrogServerSocket server, Socket socket, RequestManager manager) throws IOException
	{
		this.server = server;
		this.socket = socket;
		this.manager = manager;
		
		// Création des objets de communication
		this.out = new PrintWriter(socket.getOutputStream(), true);
		this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}
	
	private boolean isProtocolVersionValid() throws FrogException, IOException
	{
		// Coupe la communication si la version du protocol de communication est invalide
		Packet firstPacket = Packet.getPacket(this.in.readLine());
		
		if(firstPacket.getType() != PacketType.PROTOCOL_VERSION)
			throw new FrogException("Premier packet reçu incorrect");
		
		String packetJSON = firstPacket.getSerializedObject();
		JSONObject receivedObj = new JSONObject(packetJSON);
		String version = receivedObj.getString("version");
		
		boolean result = version.equals(FrogServerSocket.PROTOCOL_VERSION);
		
		JSONObject obj = new JSONObject();
		obj.put("result", result);
		Packet packetResult = new Packet(PacketType.PROTOCOL_VERSION_RESULT, obj.toString());
		this.out.println(packetResult.toJSON());

		return result;
	}
	
	@Override
	public void run() 
	{
		String reason = "";
		
		try
		{
			if(!isProtocolVersionValid())
				throw new FrogException("Mauvaise version du protocol de communication");
			
			while(this.socket.isConnected() && !this.socket.isClosed() && this.server.isRunning())
			{
				try 
				{
					String str = this.in.readLine();
					this.manager.submit(Packet.getPacket(str), this);
				} 
				catch (FrogException e) 
				{
					e.printStackTrace();
				} 
			}

		}
		catch(IOException e)
		{
			reason = String.format("(raison : %s)", "Fermeture de la communication par le client");
		}
		catch(FrogException e)
		{
			reason = String.format("(raison : %s)", e.getMessage());
		}
		finally
		{
			try { this.socket.close(); } catch (IOException ex) { ex.printStackTrace(); }
			
			// Socket closed
			System.out.println(String.format("Client %s déconnecté %s", this.socket.getInetAddress(), reason));
		}
	}

	@Override
	public void onRequestExecutionFinished(Packet result) 
	{
		// Envoi de la réponse au client
		this.out.println(result.toJSON());
	}
}
