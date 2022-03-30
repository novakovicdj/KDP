package rs.ac.bg.etf.ab_sem;

public class Producer extends Thread {
	AtomicBroadcastBuffer<Integer> buffer;

	public Producer(String name, AtomicBroadcastBuffer<Integer> buff) {
		super(name);
		this.buffer = buff;
	}
	
	@Override
	public void run() {
		super.run();
		while(true) {
			int item = (int)((Math.random()*100) + 1);
			buffer.put(item);
			System.out.println(this.getName() + " produced " + item);
			try {
				sleep((int)(Math.random()*2000));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
