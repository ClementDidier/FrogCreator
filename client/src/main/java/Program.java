import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Program 
{
	public static void main(String[] args) throws UnknownHostException, IOException
	{
		try(Socket socket = new Socket("127.0.0.1", 4000);
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter writer = new PrintWriter(socket.getOutputStream(), true))
		{
			System.out.println("Connection...");
			writer.println("1");
			System.out.println(reader.readLine());
			
			writer.println("0");
			
			while(true)
			{
				System.out.println(reader.readLine());
			}
		}
	}
}
