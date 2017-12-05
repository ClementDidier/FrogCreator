package net;

import net.socket.FrogServerSocket;

public interface IServerListener 
{
	void onStartUp(FrogServerSocket server);
}
