package rs.ac.bg.etf.kdp.lab2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class NetAB implements AB {

	String host;
	int port;
	Socket client;
	Goods[] goods;
	ObjectOutputStream out;
	ObjectInputStream in;

	@Override
	public boolean init(String host, int port) {
		this.host = host;
		this.port = port;
		try {
			this.client = new Socket(host, port);
			out = new ObjectOutputStream(client.getOutputStream());
			in = new ObjectInputStream(client.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean close() {
		try {
			out.writeUTF("end");
			out.flush();
			out.close();
			in.close();
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	@Override
	public void putGoods(String name, Goods goods) {

		try {
			out.writeUTF("put");
			out.writeUTF(name);
			out.writeObject(goods);
		//	out.flush();

			in.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Goods getGoods(String name, int id) {
		try {
			out.writeUTF("get");
			out.writeUTF(name);
			out.writeInt(id);
			out.flush();

			Goods item = (Goods) in.readObject();
			return item;
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}

	}

}
