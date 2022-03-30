package rs.ac.bg.etf;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server {

	public static void main(String[] args) {
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}

		
		try (ServerSocket server = new ServerSocket(4002);) {
			TogetherPot tp = new TogetherPotImpl();
			Registry reg = LocateRegistry.createRegistry(4001);

			TogetherPot stub = (TogetherPot) UnicastRemoteObject.exportObject(tp, 0);

			reg.rebind("/TogetherPot", stub);

			while (true) {
				Socket client = server.accept();
				new WorkingThread(client, tp).start();
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
