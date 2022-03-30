package rs.ac.bg.etf.kdp.lab2.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ABBufferRemote<T> extends Remote{

	public void put(String name, T el) throws RemoteException;
	
	public T get(String name, int id) throws RemoteException;
}
