package rs.ac.bg.etf;

import java.io.File;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface TogetherPot extends Remote {

	public void cook(String fName, File f) throws RemoteException;
	
	public File eat(String fName) throws RemoteException;
}
