package rs.ac.bg.etf.kdp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class MyConn implements Conn {

	private Socket client;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	
	public MyConn(String host, int port) {
		try {
			this.client = new Socket(host, port);
			out = new ObjectOutputStream(client.getOutputStream());
			in = new ObjectInputStream(client.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Override
	public int bringChildren(int num) {
		try {
			out.writeUTF("bring");
			out.writeInt(num);
			out.flush();
			int res = in.readInt();
			return res;
		} catch (IOException e) {
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public void takeChildren(int tick) {
		try {
			out.writeUTF("take");
			out.writeInt(tick);
			out.flush();
			
			in.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void close() {
		try {
			out.writeUTF("end");
			out.flush();
			out.close();
			in.close();
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
