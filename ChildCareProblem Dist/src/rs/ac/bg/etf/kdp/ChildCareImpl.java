package rs.ac.bg.etf.kdp;

import java.rmi.RemoteException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class ChildCareImpl implements ChildCare{

	private int numChild;
	private int numNunn;
	private int NunnCap;
	private int numWaitingC;
	private int numWaitingN;
	private static AtomicInteger ticket = new AtomicInteger(0);
	private ConcurrentHashMap<Integer, Integer> hmap;
	
	private Semaphore entry;
	private Semaphore mutex;
	private Semaphore toLeave;
	
	public ChildCareImpl(int cap) {
		this.NunnCap = cap;
		this.numChild = 0;
		this.numNunn = 0;
		this.numWaitingC = 0;
		this.numWaitingN = 0;
		this.hmap = new ConcurrentHashMap<Integer, Integer>();
		
		this.entry = new Semaphore(1, true);
		this.mutex = new Semaphore(1, true);
		this.toLeave = new Semaphore(0, true);
	}
	
	
	@Override
	public int bringChildren(int num) throws RemoteException {
		entry.acquireUninterruptibly();
		mutex.acquireUninterruptibly();
		if((numChild + num) > numNunn * NunnCap) {
			numWaitingC = num;
			mutex.release();
			toLeave.acquireUninterruptibly();
			numWaitingC = 0;
		}
		numChild += num;
		int res = ticket.getAndAdd(1);
		hmap.put(res, num);
		mutex.release();
		entry.release();
		return res;
	}

	@Override
	public void takeChildren(int tick) throws RemoteException {
		if(tick + 1 > ticket.get()) 
			return;
		mutex.acquireUninterruptibly();
		numChild -= hmap.get(tick);
		if(numWaitingC > 0 && (numChild + numWaitingC) <= numNunn * NunnCap) {
			toLeave.release();
		} else if(numWaitingN > 0 && (numChild <= NunnCap * (numNunn - 1))) {
			toLeave.release();
		} else {
			mutex.release();
		}
	}

	@Override
	public void comeToJob() throws RemoteException {
		mutex.acquireUninterruptibly();
		numNunn++;
		if(numWaitingC > 0 && (numChild + numWaitingC) <= numNunn * NunnCap) {
			toLeave.release();
		} else if(numWaitingN > 0 && (numChild <= NunnCap * (numNunn - 1))) {
			toLeave.release();
		} else {
			mutex.release();
		}
	}

	@Override
	public void goHome() throws RemoteException {
		entry.acquireUninterruptibly();
		mutex.acquireUninterruptibly();
		if(numChild > NunnCap * (numNunn - 1)) {
			numWaitingN++;
			mutex.release();
			toLeave.acquireUninterruptibly();
			numWaitingN--;
		}
		numNunn--;
		mutex.release();
		entry.release();
	}

}
