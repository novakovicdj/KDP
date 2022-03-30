package rs.ac.bg.etf.ab_mon;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MonitorAtomicBroadcastBuffer<T> implements AtomicBroadcastBuffer<T> {

	private int n;
	private int b;
	
	private T[] buffer;
	private int writeIndex;
	private int readIndex[];
	private int cnt[];
	private int numOfElems;
	private int numOfProduced;
	private int numOfRead[]; 
	
	Lock pLock;
	Condition empty;
	Condition full[];
	
	@SuppressWarnings("unchecked")
	public MonitorAtomicBroadcastBuffer(int n, int b) {
		super();
		this.n = n;
		this.b = b;
		
		buffer = (T[]) new Object[b];
		this.writeIndex = 0;
		this.readIndex = new int[n];
		this.cnt = new int[b];
		this.numOfElems = 0;
		
		this.pLock = new ReentrantLock(true);
		this.empty = pLock.newCondition();
		this.full = new Condition[n];
		this.numOfProduced = 0;
		this.numOfRead = new int[n];
		
		for(int i = 0; i < n; i++) {
			full[i] = pLock.newCondition();
		}
		
	}

	@Override
	public void put(T el, int id) {
		pLock.lock();
		try {
			//my_turn = ticket++;
			while(numOfElems == b) { //|| my_turn != next
				empty.awaitUninterruptibly();
			}
			buffer[writeIndex] = el;
			System.out.println("P" + id + " put " + el);
			numOfElems++;
			numOfProduced++;
			writeIndex = (writeIndex + 1) % b;
			
			for(int i = 0; i < n; i++) {
				full[i].signal();
			}
		} catch (Exception ex) {
			System.err.println(ex);
		} finally {
			pLock.unlock();
		}
		
	}

	@Override
	public T get(int id) {
		pLock.lock();
		if(id < 0 || id >= n)
			return null;
		try {
			//mu_turn = ticket++; 
			while(numOfElems == 0 || numOfRead[id] == numOfProduced) { //|| my_turn != next
				full[id].awaitUninterruptibly();
			}
			//next++;
			/*
			 * for(int i = 0; i < n; i++) {
			 * 		if(i != id) full[i].signal()
			 * }
			 */
			
			T item = buffer[readIndex[id]];
			System.out.println("C" + id + " took " + item);
			cnt[readIndex[id]]++;
			numOfRead[id]++;
			if(cnt[readIndex[id]] == n) {
				cnt[readIndex[id]] = 0;
				numOfElems--;
				empty.signalAll();
			}
			readIndex[id] = (readIndex[id] + 1) % b;
			return item;
		} catch(Exception ex) {
			System.err.println(ex);
			return null;
		} finally {
			pLock.unlock();
		}
	}

	
}
