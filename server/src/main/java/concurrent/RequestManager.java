package concurrent;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import net.Packet;
import net.socket.FrogServerSocket;

public class RequestManager 
{
	private BlockingQueue<FrogTask> queue;
	private RequestExecutor executor;
	
	public RequestManager(FrogServerSocket server)
	{
		this.queue = new LinkedBlockingQueue<FrogTask>();
		this.executor = new RequestExecutor(server, this.queue);
	}
	
	public synchronized void submit(Packet packet, RequestListener callback)
	{
		this.queue.add(new FrogTask(packet, callback));
	}
	
	public void start()
	{
		if(!this.executor.isAlive() || this.executor.isInterrupted())
			this.executor.start();
	}
}
