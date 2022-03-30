package rs.ac.bg.etf.kdp.lab2.rmi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import rs.ac.bg.etf.kdp.lab2.Goods;

public class RmiServer {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		if(System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
		int port = 4002;
		try {
			Registry reg = LocateRegistry.createRegistry(port);
		
			ABBufferRemote<Goods> mbx = new ABBufferRemoteImpl<>();
			ABBufferRemote<Goods> stub = (ABBufferRemote<Goods>) UnicastRemoteObject.exportObject(mbx, 0);

			reg.rebind("/buffer", stub);
			
			System.out.println("Rmi server runs on port: " + port);
			for(String s : reg.list()) {
				System.out.println(s);
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

}
