package rs.ac.bg.etf.kdp.lab2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

public class NetServer {

	
	
	public static void main(String[] args) {
		int port = 4001;
		ConcurrentHashMap<String, MonitorABBuffer<Goods>> hmap = new ConcurrentHashMap<>();
		
		try(ServerSocket server = new ServerSocket(port);) {
			while(true) {
				Socket client = server.accept();
				new WorkingThread(client, hmap).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
