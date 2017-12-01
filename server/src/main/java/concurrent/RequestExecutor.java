package concurrent;

import java.util.concurrent.BlockingQueue;

import org.json.JSONObject;

import net.Packet;
import net.PacketType;
import program.Program;

public class RequestExecutor extends Thread
{
	private BlockingQueue<FrogTask> queue;

	public RequestExecutor(BlockingQueue<FrogTask> queue)
	{
		this.queue = queue;
	}

	@Override
	public void run()
	{
		while(!Program.isStopped())
		{
			try
			{
				// Prend une tâche à réaliser
				FrogTask task = this.queue.take();
				// Extrait la requête associée
				Packet packet = task.getPacket();
				
				JSONObject obj = new JSONObject();
				PacketType resultType = PacketType.NONE;
				
				// Recherche et execution synchronisée de la requete
				switch(packet.getType())
				{
					case CONNECT:
						System.out.println("CONNECT : Do something with DB : " + packet.getSerializedObject());
						// if account OK
						obj.put("result", true);
						obj.put("token", "GENERATEDTOKEN1234567890");
						// else
						// obj.put("result", false);
						resultType = PacketType.CONNECT_RESULT;
						break;
					default:
						System.out.println("Default request received...");
						break;
				}
				
				Packet packetResult = new Packet(resultType, obj.toString()); // Resultat de l'execution // TODO : Revoir

				// Extrait le callback de la requête
				RequestListener callback = task.getCallback();
				if(callback != null)
					callback.onRequestExecutionFinished(packetResult);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
}
