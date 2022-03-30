package rs.ac.bg.etf.ab_sem;

public class Test {

	public static void main(String[] args) {
		int N = 3;
		int B = 2;
		
		AtomicBroadcastBuffer<Integer> buffer = new SemAtomicBroadcastBuffer<>(N, B);
		
		Producer p1 = new Producer("P1", buffer);
		p1.start();
		
		Producer p2 = new Producer("P2", buffer);
		p2.start();
		
		for(int i = 0; i < N; i++) {
			Consumer c = new Consumer("C"+ (i + 1), buffer, i);
			c.start();
		}
	}

}
