package rs.ac.bg.etf.kdp.lab2.net2;

import java.util.LinkedList;
import java.util.List;

public class MonitorMessageBox<T> implements MessageBox<T> {

	private List<T> buffer;
	private int cap;

	public MonitorMessageBox(int i) {
		this.cap = i;
		buffer = new LinkedList<>();
	}

	@Override
	public synchronized void put(T msg, int priority, int timeToLiveMs) {
		//int my_turn = ticket++;
		while (cap == buffer.size()) { // || my_turn != next
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
		//next++;
		//notifyAll();
		buffer.add(msg);
		notifyAll();
	}

	@Override
	public synchronized T get(int timeToWait, Status status) {
		//int my_turn = ticket++;
		while (buffer.size() == 0) {// || my_turn != next
			try {
				wait();
			} catch (InterruptedException e) {
				status.setStatus(false);
				e.printStackTrace();
				return null;
			}

		}
		//next++;
		//notifyAll();
		T item = buffer.remove(0);
		status.setStatus(true);
		notifyAll();
		return item;
	}

}
