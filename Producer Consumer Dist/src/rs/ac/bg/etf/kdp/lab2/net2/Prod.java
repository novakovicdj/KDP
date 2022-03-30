package rs.ac.bg.etf.kdp.lab2.net2;

public class Prod extends Thread {

	private MessageBox<Integer> buffer;

	public Prod(MessageBox<Integer> buffer) {
		super("Producer");
		this.buffer = buffer;
	}

	@Override
	public void run() {
		while (true) {
			int item = produce();
			buffer.put(item, 0, 0);
			
			System.out.println("Producer produced item: " + item);
		}
	}

	/**
	 * Vraca broj izmedju 1 i 100 (ukljucujuci oba).
	 * 
	 * @return
	 */
	private int produce() {
		try {
			sleep(1000 + (int) (Math.random() * 2000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return (int) (Math.random() * 100) + 1;
	}

}
