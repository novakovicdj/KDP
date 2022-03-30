package rs.ac.bg.etf.kdp.lab2;

import java.rmi.Remote;

public interface ABBuffer<T> extends Remote{
	
	public void put(T el);
	
	public T get(int id);
}
