package rs.ac.bg.etf.kdp.lab2.net2;

public class Cons extends Thread {

	private MessageBox<Integer> buff;

	public Cons(MessageBox<Integer> buffer) {
		super("Consumer");
		buff = buffer;
	}

	@Override
	public void run() {
		while (true) {
			Status myStatus;
			myStatus = new BooleanStatus(false);

			Integer item = buff.get(Integer.MAX_VALUE, myStatus);

			if (myStatus.getStatus()) {
				// OK, uzet element
				consume(item);
			}

		}
	}

	private void consume(Integer item) {
		try {
			sleep(1000 + (int) (Math.random() * 9000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(String.format("Consumer consumed item %d", item.intValue()));
	}
}
