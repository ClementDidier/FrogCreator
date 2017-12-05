package net;

import java.net.Socket;

public interface IClientListener 
{
	void onClientAccept(Socket client);
}
