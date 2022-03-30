package rs.ac.bg.etf.kdp.lab2.net2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class WorkingThread extends Thread {

	private MessageBox<Integer> buf;
	private Socket cl;
	
	public WorkingThread(Socket client, MessageBox<Integer> buffer) {
		this.buf = buffer;
		this.cl = client;
	}

	@Override
	public void run() {
		try(Socket c = this.cl;
				ObjectOutputStream out = new ObjectOutputStream(c.getOutputStream());
				ObjectInputStream in = new ObjectInputStream(c.getInputStream())) {
			String op = in.readUTF();
			switch(op) {
			case "put": 
				Integer item = (Integer) in.readObject();
				this.buf.put(item, MIN_PRIORITY, MAX_PRIORITY);
				out.writeUTF("ACK");
				out.flush();
				break;
			
			case "get": 
				Status sts = (Status) in.readObject();
				Integer res = buf.get(MAX_PRIORITY, sts);
				out.writeObject(res);
				out.writeObject(sts);
				break;
			default: 
				System.err.println("***Nepoznata operacija***");
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
