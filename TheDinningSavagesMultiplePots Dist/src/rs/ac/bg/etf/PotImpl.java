package rs.ac.bg.etf;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class PotImpl implements Pot {

	@SuppressWarnings("unused")
	private int cap;
	private List<File> food;

	private Semaphore mutex;
	private Semaphore cookSem;
	private Semaphore savageSem;

	public PotImpl(int c) {
		this.cap = c;
		this.food = new LinkedList<>();

		mutex = new Semaphore(1, true);
		cookSem = new Semaphore(0, true);
		savageSem = new Semaphore(0, true);
	}

	@Override
	public void cook(File f) {
		cookSem.acquireUninterruptibly();
		food.add(f);
		savageSem.release();
	}

	@Override
	public File eat() {
		mutex.acquireUninterruptibly();
		if (food.size() == 0) {
			cookSem.release();
			savageSem.acquireUninterruptibly();
		}
		File res = food.remove(0);
		mutex.release();
		return res;
	}
	

}
