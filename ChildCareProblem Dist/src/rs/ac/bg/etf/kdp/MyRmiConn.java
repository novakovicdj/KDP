package rs.ac.bg.etf.kdp;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class MyRmiConn implements RmiConn {

	ChildCare buf;
	
	public MyRmiConn(String host, int port) throws RemoteException{
		if(System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
		try {
			Registry reg = LocateRegistry.getRegistry(host, port);
			buf = (ChildCare) reg.lookup("/childCare");
		} catch (RemoteException | NotBoundException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void comeToJob() throws RemoteException {
		buf.comeToJob();
	}

	@Override
	public void goHome() throws RemoteException {
		buf.goHome();
	}

}
