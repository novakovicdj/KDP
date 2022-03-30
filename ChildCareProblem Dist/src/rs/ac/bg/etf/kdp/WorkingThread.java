package rs.ac.bg.etf.kdp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class WorkingThread extends Thread {

	private Socket client;
	private ChildCare cCare;
	
	public WorkingThread(Socket cl, ChildCare cc) {
		this.client = cl;
		this.cCare = cc;
	}

	
	@Override
	public void run() {
		try(Socket cl = this.client;
				ObjectOutputStream out = new ObjectOutputStream(cl.getOutputStream());
				ObjectInputStream in = new ObjectInputStream(client.getInputStream())) {
			boolean end = false;
			while(!end) {
				String op = in.readUTF();
				switch(op) {
				case "bring":
					int num = in.readInt();
					int res = cCare.bringChildren(num);
					out.writeInt(res);
					out.flush();
					break;
				case "take":
					int tick = in.readInt();
					cCare.takeChildren(tick);
					
					out.writeObject("DONE");
					break;
				case "end":
					end = true;
					break;
				default:
					System.err.println(String.format("*** Operation %s not known", op));
						
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
