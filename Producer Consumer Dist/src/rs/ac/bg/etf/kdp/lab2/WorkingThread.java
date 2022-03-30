package rs.ac.bg.etf.kdp.lab2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

public class WorkingThread extends Thread {

	private Socket c;
	private ConcurrentHashMap<String, MonitorABBuffer<Goods>> hmap;

	public WorkingThread(Socket c, ConcurrentHashMap<String, MonitorABBuffer<Goods>> hmap) {
		super();
		this.c = c;
		this.hmap = hmap;
	}
	
	

	@Override
	public void run() {
		try (Socket cl = this.c;
				ObjectOutputStream out = new ObjectOutputStream(cl.getOutputStream());
				ObjectInputStream in = new ObjectInputStream(cl.getInputStream())) {
			boolean end = false;
			while (!end) {
				
				String op = in.readUTF();

				if ("put".equalsIgnoreCase(op)) {
					String name = in.readUTF();
					Goods obj = (Goods) in.readObject();
					if (hmap.containsKey(name)) {
						hmap.get(name).put(obj);
					} else {
						hmap.putIfAbsent(name, new MonitorABBuffer<>(1, 3));
						hmap.get(name).put(obj);
					}
					out.writeObject("ACK");
				} else if ("get".equalsIgnoreCase(op)) {
					String name = in.readUTF();
					int id = in.readInt();
					while (!hmap.containsKey(name)) {
						hmap.putIfAbsent(name, new MonitorABBuffer<>(1, 3));
					}
					Goods el = hmap.get(name).get(id);
					out.writeObject(el);
				} else if("end".equalsIgnoreCase(op)) {
					end = true;
				}
				else {
					System.err.println(String.format("***Nepoznata %s operacija!***", op));
				}
			}

		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
