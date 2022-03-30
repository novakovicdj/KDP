package rs.ac.bg.etf.ab_mon;

public class Consumer extends Thread {
	AtomicBroadcastBuffer<Integer> buffer;
	int myId;
	public Consumer(String name, AtomicBroadcastBuffer<Integer> buffer, int id) {
		super(name);
		this.buffer = buffer;
		this.myId = id;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		while(true) {
			@SuppressWarnings("unused")
			int item = buffer.get(myId);
			try {
				sleep((int)(Math.random()*1500));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
