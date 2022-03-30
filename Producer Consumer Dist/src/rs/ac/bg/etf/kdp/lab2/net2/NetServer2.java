package rs.ac.bg.etf.kdp.lab2.net2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class NetServer2 {
	
	public static void main(String[] args) {
		int port = 4001;
		MessageBox<Integer> buffer = new LocksMessageBox<>(3);
		try (ServerSocket server = new ServerSocket(port);) {
			while(true) {
				Socket client = server.accept();
				new WorkingThread(client, buffer).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
