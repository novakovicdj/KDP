package rs.ac.bg.etf.csv_file;

import java.io.BufferedReader;
import java.io.FileReader;

public class Producer extends Thread {

	SemAtomicBroadcasBuffer<String> buffer;
	String path;
	
	public Producer(SemAtomicBroadcasBuffer<String> buff, String p) {
		super("Producer");
		this.buffer = buff;
		this.path = p;
	}

	@Override
	public void run() {
		String row;
		try(FileReader file = new FileReader("./"+this.path)) {
			BufferedReader tsvReader = new BufferedReader(file);
			tsvReader.readLine();
			while((row = tsvReader.readLine()) != null) {
				this.buffer.put(row);
			}
			this.buffer.put(null);
		} catch (Exception ex) {
			System.err.println(ex);
		}
	}

}
