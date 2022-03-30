package rs.ac.bg.etf;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

@SuppressWarnings("serial")
public class NetConn implements Conn {

	private String host;
	private int port;
	
	public NetConn(String host, int port) {
		this.host = host;
		this.port = port;
	}

	@Override
	public File eat(String food) {
		try (Socket client = new Socket(host, port);
				ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
				ObjectInputStream in = new ObjectInputStream(client.getInputStream());){
			out.writeUTF(food);
			out.flush();
			
			File f = (File) in.readObject();
			return f;
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		
	}

}
