package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.junit.Test;

import net.DataPacket;
import net.request.ConnectionRequest;
import net.request.NetRequest;
import net.request.NetRequestType;

public class ConnectionRequestTest 
{
	@Test
	public void connectionRequestTest() throws UnsupportedEncodingException
	{
		NetRequest connectionRequest = new ConnectionRequest("compte", "password");
		DataPacket packet = connectionRequest.getData();
		packet.resetPointer();
		assertEquals("Le type de la requête n'est pas correcte", NetRequestType.CONNECT.getByte().byteValue(), packet.readByte());
		assertEquals("Le nom de compte en entrée n'est pas égal à celui en sortie", "compte", packet.readString());
		
		byte[] hash = null;
		try {
			hash = MessageDigest.getInstance("MD5").digest("password".getBytes());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			fail();
		}
		byte[] pwd = packet.readToEnd();
		String strPwd = new String(pwd);
		
		assertEquals("Le hash du password en entrée n'est pas égal à celui en sortie", new String(hash), strPwd);
	}
}
