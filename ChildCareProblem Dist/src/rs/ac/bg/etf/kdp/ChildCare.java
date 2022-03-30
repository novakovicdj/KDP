package rs.ac.bg.etf.kdp;


import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChildCare extends Remote{

	public int bringChildren(int num) throws RemoteException;
	
	public void takeChildren(int tick) throws RemoteException;
	
	public void comeToJob() throws RemoteException;
	
	public void goHome() throws RemoteException;
}
