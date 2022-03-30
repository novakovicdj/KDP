package rs.ac.bg.etf.ab_mon;

public class Test {

	public static void main(String[] args) {
		
		int n = 3;
		int b = 2;
		
		AtomicBroadcastBuffer<Integer> buffer = new MonitorAtomicBroadcastBuffer<>(n, b);

		Producer p1 = new Producer("P1", buffer, 1);
		p1.start();
		
		Producer p2 = new Producer("P2", buffer, 2);
		p2.start();
		
		for(int i = 0; i < n; i++) {
			Consumer c = new Consumer("C" + (i+1),buffer, i);
			c.start();
		}
		
	}

}
