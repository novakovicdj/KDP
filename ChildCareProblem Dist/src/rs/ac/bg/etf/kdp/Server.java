package rs.ac.bg.etf.kdp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server {

	public static void main(String[] args) {
		int portNet = 4001;
		int portRmi = 4002;
		if(System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
		try {
			Registry reg = LocateRegistry.createRegistry(portRmi);
			ChildCare cc = new ChildCareImpl(3);
			ChildCare stub = (ChildCare) UnicastRemoteObject.exportObject(cc, 0);
			reg.rebind("/childCare", stub);
			
			try(ServerSocket server = new ServerSocket(portNet);) {
				while(true) {
					Socket cl = server.accept();
					new WorkingThread(cl, cc).start();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		
	}

}
