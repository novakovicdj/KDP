package rs.ac.bg.etf.kdp.lab2.net2;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LocksMessageBox<T> implements MessageBox<T> {

	private List<T> buffer;
	private int capacity;
	private Lock lock;
	private Condition write;
	private Condition read;
	
	public LocksMessageBox(int cap) {
		this.capacity = cap;
		this.buffer = new LinkedList<>();
		this.lock = new ReentrantLock(true);
		this.write = lock.newCondition();
		this.read = lock.newCondition();
	}
	
	@Override
	public void put(T msg, int priority, int timeToLiveMs) {
		try {
			lock.lockInterruptibly();
			if(this.capacity == buffer.size()) {
				write.awaitUninterruptibly();
			}
			buffer.add(msg);
			read.signal();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
		
	}

	@Override
	public T get(int timeToWait, Status status) {
		try {
			lock.lockInterruptibly();
			if(this.buffer.size() == 0) {
				read.awaitUninterruptibly();
			}
			T res = buffer.remove(0);
			status.setStatus(true);
			write.signal();
			return res;
		} catch (InterruptedException e) {
			status.setStatus(false);
			e.printStackTrace();
			return null;
		} finally {
			lock.unlock();
		}
		
	}

}
