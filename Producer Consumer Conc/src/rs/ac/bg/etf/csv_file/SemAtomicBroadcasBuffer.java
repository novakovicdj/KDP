package rs.ac.bg.etf.csv_file;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class SemAtomicBroadcasBuffer<T> {

	private int n;
	private int b;
	private int id;
	private int cnt;

	private T buffer[];
	private Queue<Integer> writeIndex;
	private Queue<Integer> readIndex;

	private Semaphore empty;
	private Semaphore full;
	private Semaphore mutexW;
	private Semaphore mutexR;

	private boolean finished;

	@SuppressWarnings("unchecked")
	public SemAtomicBroadcasBuffer(int n, int b) {
		super();
		this.n = n;
		this.b = b;
		this.buffer = (T[]) new Object[b];
		this.empty = new Semaphore(b, true);
		this.full = new Semaphore(0);
		this.mutexW = new Semaphore(1);
		this.mutexR = new Semaphore(1);
		this.writeIndex = new LinkedList<Integer>();
		this.readIndex = new LinkedList<Integer>();
		this.finished = false;
		this.cnt = 0;

		for (int i = 0; i < b; i++) {
			writeIndex.add(i);
		}
	}

	public void put(T el) {
		// if(el != null) {
		if (el == null) {
			finished = true;
			this.cnt--;
		}
		empty.acquireUninterruptibly();
		mutexW.acquireUninterruptibly();
		int wi = writeIndex.poll();
		this.cnt++;
		mutexW.release();
		buffer[wi] = el;
		mutexR.acquireUninterruptibly();
		readIndex.add(wi);
		mutexR.release();
		full.release();
		// } else {

		// }
	}

	public T get(int id) {
		mutexR.acquireUninterruptibly();
		if (id < 0 || id >= n || (finished && readIndex.size() == 0)) {
			mutexR.release();
			return null;
		}
		mutexR.release();

		full.acquireUninterruptibly();
		mutexR.acquireUninterruptibly();
		int ri = readIndex.poll();
		mutexR.release();
		T item = buffer[ri];
		buffer[ri] = null;
		mutexW.acquireUninterruptibly();
		if (item == null) {
			while (full.hasQueuedThreads()) {
				full.release();
				readIndex.add(0);
			}
		}
		this.cnt--;
		writeIndex.add(ri);
		mutexW.release();
		empty.release();
		return item;
	}

	public int getSize() {
		return this.cnt;
	}
}
