package rs.ac.bg.etf.ab_reg;

public class Producer extends Thread {
	AtomicBroadcastBuffer<Integer> buffer;
	int myId;

	public Producer(String name, AtomicBroadcastBuffer<Integer> buff, int id) {
		super(name);
		this.buffer = buff;
		this.myId = id;
	}
	
	@Override
	public void run() {
		super.run();
		while(true) {
			int item = (int)((Math.random()*100) + 1);
			buffer.put(item, myId);
			//System.out.println(this.getName() + " put " + item);
			
			try {
				sleep((int)(Math.random()*2000));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
