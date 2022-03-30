package rs.ac.bg.etf.ab_mon;

public interface AtomicBroadcastBuffer<T> {

	void put(T el, int id);
	T get(int id);
}
