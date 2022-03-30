package rs.ac.bg.etf.ab_mon;

public class Producer extends Thread {
	AtomicBroadcastBuffer<Integer> buffer;
	private int id;
	public Producer(String name, AtomicBroadcastBuffer<Integer> buffer, int id) {
		super(name);
		this.buffer = buffer;
		this.id = id;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		while(true) {
			int item = (int)(Math.random()*100 + 1);
			buffer.put(item, this.id);
			try {
				sleep((int)(Math.random()*2000));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
