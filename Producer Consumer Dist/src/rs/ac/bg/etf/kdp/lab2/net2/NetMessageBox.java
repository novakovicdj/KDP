package rs.ac.bg.etf.kdp.lab2.net2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class NetMessageBox<T> implements MessageBox<T> {

	private String host;
	private int port;

	public NetMessageBox(String h, int p) {
		this.host = h;
		this.port = p;
	}

	@Override
	public void put(T msg, int priority, int timeToLiveMs) {
		try (Socket s = new Socket(host, port);
				ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
				ObjectInputStream in = new ObjectInputStream(s.getInputStream())) {
			out.writeUTF("put");
			out.writeObject(msg);

			in.readUTF();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public T get(int timeToWait, Status status) {
		try (Socket s = new Socket(host, port);
				ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
				ObjectInputStream in = new ObjectInputStream(s.getInputStream())) {
			out.writeUTF("get");
			out.writeObject(status);

			T item = (T) in.readObject();
			Status sts = (Status) in.readObject();
			status.setStatus(sts.getStatus());
			return item;
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}

	}

}
