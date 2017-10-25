package net.request;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ConnectionRequest extends NetRequest 
{
	public ConnectionRequest(String account, String password)
	{
		super(NetRequestType.CONNECT);
		this.data.write(account);
		try {
			this.data.write(MessageDigest.getInstance("MD5").digest(password.getBytes()));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
}
