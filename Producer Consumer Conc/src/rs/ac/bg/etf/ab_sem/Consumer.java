package rs.ac.bg.etf.ab_sem;

public class Consumer extends Thread {
	AtomicBroadcastBuffer<Integer> buffer;
	int id;
	
	public Consumer(String name, AtomicBroadcastBuffer<Integer> buff, int id) {
		super(name);
		this.buffer = buff;
		this.id = id;
	}

	@Override
	public void run() {
		super.run();
		while(true) {
			int item = buffer.get(id);
			System.out.println(this.getName() + " took " + item);
			try {
				sleep((int)(Math.random()*1500));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
