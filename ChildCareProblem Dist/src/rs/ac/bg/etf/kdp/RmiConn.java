package rs.ac.bg.etf.kdp;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RmiConn extends Remote {

	public void comeToJob() throws RemoteException;
	
	public void goHome() throws RemoteException;
}
