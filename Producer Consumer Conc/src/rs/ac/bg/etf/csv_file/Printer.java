package rs.ac.bg.etf.csv_file;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class Printer extends Thread {

	private SemAtomicBroadcasBuffer<String> fromCon; 
	private SemAtomicBroadcasBuffer<String> fromComb;
	private ConcurrentHashMap<Integer, Double> cons;
	
	private boolean finished;
	private boolean consFinished;
	
	public Printer(SemAtomicBroadcasBuffer<String> c, SemAtomicBroadcasBuffer<String> c1) {
		super("Printer");
		this.fromComb = c1;
		this.fromCon = c;
		this.cons = new ConcurrentHashMap<Integer, Double>();
		this.finished = false;
		this.consFinished = false;

	}

	@Override
	public void run() {
		while(!finished) {
			String h = this.fromCon.get(0);
			if(h == null) {
				this.consFinished = true;
			} else {
				String str = h.substring(1, h.length()-1);
				String strData[] = str.split("=");
				cons.put(Integer.parseInt(strData[0]), Double.parseDouble(strData[1]));
				for(Entry<Integer, Double> set : cons.entrySet()) {
					System.out.println(set.getKey() + " : " + set.getValue());
				}
				System.out.println();
			}
			
			if(consFinished) {
				String strCmb = this.fromComb.get(0);
				strCmb = strCmb.substring(1, strCmb.length()-1);
				String strCmbData[] = strCmb.split(",");
				for(int i = 0; i < strCmbData.length; i++) {
					System.out.println(strCmbData[i]);
				}
				finished = true;
				
			}
			
			/*for(Entry<Integer, Integer> s :h.entrySet())
				this.cons.put(s.getKey(), s.getValue());
			
			for(Entry<Integer, Integer> set : cons.entrySet()) {
				System.out.println(set.getKey() + " - " + (set.getKey() + 9) + " " + set.getValue());
			}
			System.out.println();
			*/
			
		}
	}

}
