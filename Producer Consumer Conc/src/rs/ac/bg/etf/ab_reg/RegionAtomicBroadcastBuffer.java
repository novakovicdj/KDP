package rs.ac.bg.etf.ab_reg;

public class RegionAtomicBroadcastBuffer<T> implements AtomicBroadcastBuffer<T> {
	int n;
	int b;
	T[] buffer;
	int numOfElems;
	int numOfProduced;
	int numOfRead[];
	static int next = 0;
	static int ticket = 0;
	int readIndex[];
	int writeIndex;
	int cnt[];
	int my_turn;

	@SuppressWarnings("unchecked")
	public RegionAtomicBroadcastBuffer(int n, int b) {
		super();
		this.n = n;
		this.b = b;
		this.readIndex = new int[n];
		this.buffer = (T[]) new Object[b];
		this.numOfElems = 0;
		this.cnt = new int[b];
		this.numOfProduced = 0;
		this.numOfRead = new int[n];
	}

	@Override
	public void put(T el, int id) {
		synchronized (buffer) {
			while (numOfElems == b) {
				try {
					buffer.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			buffer[writeIndex] = el;
			System.out.println("P" + id + " put " + el);
			numOfElems++;
			numOfProduced++;
			writeIndex = (writeIndex + 1) % b;
			buffer.notifyAll();
		}

	}

	@Override
	public T get(int id) {
		if (id < 0 || id >= n)
			return null;
		synchronized (buffer) {
			my_turn = ticket++;
			while (numOfElems == 0 || numOfRead[id] == numOfProduced || my_turn != next) { // || my_turn != next
				try {
					buffer.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			next++;
			buffer.notifyAll();
			T item = buffer[readIndex[id]];
			System.out.println("C" + id + " took" + item);
			cnt[readIndex[id]]++;
			numOfRead[id]++;
			if (cnt[readIndex[id]] == n) {
				cnt[readIndex[id]] = 0;
				numOfElems--;
				buffer.notifyAll();
			}
			readIndex[id] = (readIndex[id] + 1) % b;
			return item;
		}
	}

}
