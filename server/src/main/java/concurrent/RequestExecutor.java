package concurrent;

import java.util.concurrent.BlockingQueue;

import net.Packet;
import net.PacketType;
import program.Program;
import utils.FrogException;

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
				Packet packetResult = new Packet(PacketType.RESULT, "Nothing"); // Resultat de l'execution // TODO : Revoir

				// Recherche et execution synchronisée de la requete
				switch(packet.getPacketType())
				{
					case CONNECT:
						System.out.println("Connect request synch execution DONE");
						break;
					default:
						System.out.println("Default request received...");
						break;
				}

				// Extrait le callback de la requête
				RequestExecutionFinishedListener callback = task.getCallback();
				if(callback != null)
					callback.requestExecutionFinished(packetResult);
			}
			catch (InterruptedException | FrogException e)
			{
				e.printStackTrace();
			}
		}
	}
}
