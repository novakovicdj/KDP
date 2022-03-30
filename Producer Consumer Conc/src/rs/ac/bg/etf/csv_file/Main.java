package rs.ac.bg.etf.csv_file;

import java.time.Clock;
import java.util.concurrent.ConcurrentHashMap;

public class Main {

	public static void main(String[] args) {

		int consumersNumber = 50;
		int N = 500;
		String fileName = "data.tsv"; // your file name
		
		
		
		SemAtomicBroadcasBuffer<String> buffer = new SemAtomicBroadcasBuffer<>(consumersNumber, consumersNumber);
		SemAtomicBroadcasBuffer<String> cToC = new SemAtomicBroadcasBuffer<>(1, consumersNumber);
		SemAtomicBroadcasBuffer<String> cToP = new SemAtomicBroadcasBuffer<>(1, consumersNumber);
		SemAtomicBroadcasBuffer<String> cmbToP = new SemAtomicBroadcasBuffer<>(1, consumersNumber);
		long startT = System.currentTimeMillis();
		Producer producer = new Producer(buffer, fileName);
		producer.start();

		for (int i = 0; i < 5; i++) {
			Consumer consumer = new Consumer(i, buffer, N, cToP, cToC, 5);
			consumer.start();
		}

		Combiner combiner = new Combiner(cToC,cmbToP, 1);
		combiner.start();

		Printer printer = new Printer(cToP, cmbToP);
		printer.start();

		try {
			printer.join();
			long endT = System.currentTimeMillis();
			System.out.println();
			System.out.println(endT - startT);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
