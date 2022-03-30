package rs.ac.bg.etf.kdp.lab2.rmi;

import java.rmi.RemoteException;
import java.util.concurrent.ConcurrentHashMap;

import rs.ac.bg.etf.kdp.lab2.ABBuffer;
import rs.ac.bg.etf.kdp.lab2.MonitorABBuffer;

public class ABBufferRemoteImpl<T> implements ABBufferRemote<T> {

	ConcurrentHashMap<String, ABBuffer<T>> hmap;

	public ABBufferRemoteImpl() {
		hmap = new ConcurrentHashMap<String, ABBuffer<T>>();
	}

	@Override
	public void put(String name, T el) throws RemoteException {
		if (hmap.containsKey(name)) {
			hmap.get(name).put(el);
		} else {
			hmap.putIfAbsent(name, new MonitorABBuffer<T>(1, 3));
			hmap.get(name).put(el);
		}
	}

	@Override
	public T get(String name, int id) throws RemoteException {
		T item = null;
		if (hmap.containsKey(name)) {
			item = hmap.get(name).get(id);
		} else {
			hmap.putIfAbsent(name, new MonitorABBuffer<T>(1,3));
			item = hmap.get(name).get(id);
		}
		return item;
	}

}
