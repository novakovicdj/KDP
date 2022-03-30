package rs.ac.bg.etf;

import java.io.File;
import java.rmi.RemoteException;
import java.util.concurrent.ConcurrentHashMap;

public class TogetherPotImpl implements TogetherPot {

	private ConcurrentHashMap<String, Pot> hmap;

	public TogetherPotImpl() throws RemoteException {
		hmap = new ConcurrentHashMap<String, Pot>();
	}

	public void cook(String fName, File f) throws RemoteException {
		if (!hmap.containsKey(fName)) {
			hmap.putIfAbsent(fName, new PotImpl(1));
		}
		hmap.get(fName).cook(f);
	}

	public File eat(String fName) throws RemoteException {
		if (!hmap.containsKey(fName)) {
			hmap.putIfAbsent(fName, new PotImpl(1));
		}
		File res = hmap.get(fName).eat();
		return res;
	}
}
