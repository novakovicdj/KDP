package rs.ac.bg.etf.kdp.lab2;

import java.rmi.Remote;

public class MonitorABBuffer<T> implements ABBuffer<T>, Remote {

	private int B;
	private int N;
	private T[] buffer;
	private int writeIndex;
	private int[] readIndex;
	private int numOfProduced;
	private int[] numOfRead;
	private int cnt[];
	private int numOfElems;
	
	@SuppressWarnings("unchecked")
	public MonitorABBuffer(int cap, int num) {
		this.B = cap;
		this.N = num;
		this.buffer = (T[]) new Object[cap];
		this.readIndex = new int[num];
		this.numOfRead = new int[num];
		this.cnt = new int[cap];
		this.numOfElems = 0;
		this.numOfProduced = 0;
		this.writeIndex = 0;
	}
	
	
	@Override
	public synchronized void put(T el) {
		while(numOfElems == this.B) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		buffer[writeIndex] = el;
		numOfElems++;
		writeIndex = (writeIndex + 1) % B;
		numOfProduced++;
		notifyAll();
	}

	@Override
	public synchronized T get(int id) {
		while(numOfElems == 0 || numOfRead[id] == numOfProduced) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		T el = buffer[readIndex[id]];
		cnt[readIndex[id]]++;
		numOfRead[id]++;
		if(cnt[readIndex[id]] == this.N) {
			cnt[readIndex[id]] = 0;
			numOfElems--;
			notifyAll();
		}
		readIndex[id] = (readIndex[id] + 1) % B;
		return el;
	}

}
