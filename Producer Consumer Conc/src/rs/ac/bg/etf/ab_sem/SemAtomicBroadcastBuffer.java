package rs.ac.bg.etf.ab_sem;

import java.util.concurrent.Semaphore;

public class SemAtomicBroadcastBuffer<T> implements AtomicBroadcastBuffer<T> {

	private int n;
	private int b;
	private int cnt[];
	private int readIndex[];
	private int writeIndex;
	
	private T[] buffer;
	
	private Semaphore empty;
	private Semaphore full[];
	private Semaphore mutexC[];
	private Semaphore mutexP;
	
	
	
	@SuppressWarnings("unchecked")
	public SemAtomicBroadcastBuffer(int n, int b) {
		super();
		this.n = n;
		this.b = b;
		this.buffer = (T[]) new Object[b];
		this.empty = new Semaphore(b);
		this.full = new Semaphore[n];
		this.mutexC = new Semaphore[b];
		this.cnt = new int[b];
		this.readIndex = new int[n];
		this.writeIndex = 0;
		this.mutexP = new Semaphore(1);
		
		for(int i = 0; i < n; i++) {
			full[i] = new Semaphore(0);
		}
		
		for(int i = 0; i < b; i++) {
			mutexC[i] = new Semaphore(1);
		}
	}

	@Override
	public void put(T el) {
		empty.acquireUninterruptibly();
		mutexP.acquireUninterruptibly();
		buffer[writeIndex] = el;
		writeIndex = (writeIndex + 1) % b;
		mutexP.release();
		
		for(int i = 0; i < full.length; i++) {
			full[i].release();
		}
	}

	@Override
	public T get(int id) {
		if(id < 0 || id >= this.n)
			return null;
		
		full[id].acquireUninterruptibly();
		T item = buffer[readIndex[id]];
		mutexC[readIndex[id]].acquireUninterruptibly();
		cnt[readIndex[id]]++;
		if(cnt[readIndex[id]] == n) {
			cnt[readIndex[id]] = 0;
			empty.release();
		}
		mutexC[readIndex[id]].release();
		readIndex[id] = (readIndex[id] + 1) % b;
		return item;
	}

	
	
}
