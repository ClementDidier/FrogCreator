package concurrent;

import java.util.concurrent.BlockingQueue;

import com.frog.net.Request;
import com.frog.net.RequestResult;

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
				FrogTask task = this.queue.take();
				Request request = task.getRequest();
				RequestResult result = new RequestResult(); // Resultat de l'execution
				
				// Recherche et execution synchronisée de la requete
				switch(request.getType())
				{
					case NONE:
						break;
					default:
						break;
				}
				
				// Callback
				RequestExecutionFinishedListener callback = task.getCallback();
				if(callback != null)
					callback.requestExecutionFinished(result);
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
	}
}
