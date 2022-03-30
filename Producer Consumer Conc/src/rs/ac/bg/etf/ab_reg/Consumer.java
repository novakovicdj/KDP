package rs.ac.bg.etf.ab_reg;

public class Consumer extends Thread {
	AtomicBroadcastBuffer<Integer> buffer;
	int myId;

	public Consumer(String name, AtomicBroadcastBuffer<Integer> buff, int id) {
		super(name);
		this.buffer = buff;
		this.myId = id;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		while(true) {
			int item = buffer.get(myId);
			//System.out.println(this.getName() + " took " + item);
			
			try {
				sleep((int)(Math.random()*1500));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
