package rs.ac.bg.etf.ab_sem;

public interface AtomicBroadcastBuffer<T> {

	void put(T el);
	T get(int id);
	
}
