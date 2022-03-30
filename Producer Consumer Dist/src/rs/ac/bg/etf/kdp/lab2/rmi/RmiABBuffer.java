package rs.ac.bg.etf.kdp.lab2.rmi;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RmiABBuffer<T> implements ABBufferRemote<T> {

	ABBufferRemote<T> buf;

	@SuppressWarnings("unchecked")
	public RmiABBuffer(String host, int port) {
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
		try {
			Registry reg = LocateRegistry.getRegistry(host, port);
			buf = (ABBufferRemote<T>) reg.lookup("/buffer");
		} catch (RemoteException | NotBoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void put(String name, T el) {
		try {
			buf.put(name, el);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public T get(String name, int id) {
		T res = null;
		try {
			res = buf.get(name, id);
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}
		return res;
	}

}
