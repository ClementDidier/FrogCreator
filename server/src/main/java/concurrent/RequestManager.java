package concurrent;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import net.Packet;

public class RequestManager 
{
	private BlockingQueue<FrogTask> queue;
	private RequestExecutor executor;
	
	public RequestManager()
	{
		this.queue = new LinkedBlockingQueue<FrogTask>();
		this.executor = new RequestExecutor(this.queue);
		this.executor.start();
	}
	
	public synchronized void submit(Packet packet, RequestExecutionFinishedListener callback)
	{
		this.queue.add(new FrogTask(packet, callback));
	}
}
