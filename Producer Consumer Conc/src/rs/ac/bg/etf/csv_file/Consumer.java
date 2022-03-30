package rs.ac.bg.etf.csv_file;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;


public class Consumer extends Thread {

	private SemAtomicBroadcasBuffer<String> buffer;
	private SemAtomicBroadcasBuffer<String> conToPri;
	private SemAtomicBroadcasBuffer<String> conToCmb;
	private ConcurrentHashMap<Integer, Double> osobe;
	static int numOfFinished = 0;
	static Semaphore mutex = new Semaphore(1);
	private int myID;
	private double myNum;
	private int numOfConsumers;
	private int N;
	private double myNumSum;
	private String line;
	private double cnt;
	public Consumer(int id, SemAtomicBroadcasBuffer<String> buff, int num,SemAtomicBroadcasBuffer<String> b1, SemAtomicBroadcasBuffer<String> b2, int numOfC) {
		super("Consumer" + id);
		this.buffer = buff;
		this.myID = id;
		this.myNum = 0;
		this.myNumSum = 0;
		this.N = num;
		this.osobe = new ConcurrentHashMap<Integer, Double>();
		this.conToPri = b1;
		this.conToCmb = b2;
		this.cnt = 0;
		this.numOfConsumers = numOfC;
	}

	@Override
	public void run() {
		while(true) {
			line = this.buffer.get(myID);
			this.parseLine(line);
			if(line == null) {
				break;
			}
		}
		mutex.acquireUninterruptibly();
		numOfFinished++;
		if(numOfFinished == this.numOfConsumers) {
			this.conToCmb.put(null);
			this.conToPri.put(null);
		}
		mutex.release();
	}

	private void parseLine(String line) {
		if(line != null) {
			Boolean act = false;
			String[] args = line.split("\t");
			//System.out.println(line);
			this.myNum++;
			String[] profesije = args[4].split(",");
			for(int i = 0; i < profesije.length; i++) {
				if(profesije[i].equals("actor") || profesije[i].equals("actress")) {
					act = true; 
					break;
				}
				
			}
			if(act && args[3].equals("\\N")) {
				if(!args[2].equals("\\N")) {
					int dek = Integer.parseInt(args[2]) / 10 * 10;
					double kol = osobe.get(dek) == null? 0:osobe.get(dek);
					osobe.put(dek, kol+1);
				} else {
					double kol = osobe.get(0) == null? 0:osobe.get(0);
					osobe.put(0, kol + 1);
				}
			}
			if(myNum == this.N) {
				//ubaci broj obradjenih osoba u jedan delj obj
				this.myNumSum += this.myNum;
				this.myNum = 0;
				ConcurrentHashMap<Integer, Double> h = new ConcurrentHashMap<Integer,Double>();
				h.put(this.myID, this.myNumSum);
				this.conToPri.put(h.toString());
				//ubaci obradu u drugi deljeni obj
				this.conToCmb.put(osobe.toString());
				osobe.clear();
				
			}
		} else {
			//ubaci broj obradjenih osoba u jedan delj obj
			this.myNumSum += this.myNum;
			this.myNum = 0;
			ConcurrentHashMap<Integer, Double> h = new ConcurrentHashMap<Integer,Double>();
			h.put(this.myID, this.myNumSum);
			this.conToPri.put(h.toString());
			//ubaci obradu u drugi deljeni obj
			this.conToCmb.put(osobe.toString());
			osobe.clear();
		}
	}

}
