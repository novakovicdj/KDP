package rs.ac.bg.etf;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class WorkingThread extends Thread {

	private TogetherPot tPot;
	private Socket cl;
	
	public WorkingThread(Socket client, TogetherPot p) {
		this.tPot = p;
		this.cl = client;
	}

	
	@Override
	public void run() {
		try(Socket client = this.cl;
				ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
				ObjectInputStream in = new ObjectInputStream(client.getInputStream());) {
			String food = in.readUTF();
			File res = tPot.eat(food);
			out.writeObject(res);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
