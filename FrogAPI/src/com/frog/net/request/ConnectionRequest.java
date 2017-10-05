package com.frog.net.request;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.frog.net.Request;
import com.frog.net.RequestType;

public class ConnectionRequest extends Request 
{
	public ConnectionRequest(String account, String password)
	{
		super(RequestType.CONNECT);
		this.data.write(account);
		try {
			this.data.write(MessageDigest.getInstance("MD5").digest(password.getBytes()));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
}
