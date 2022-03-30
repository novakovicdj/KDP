package rs.ac.bg.etf.csv_file;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;

public class Combiner extends Thread {

	SemAtomicBroadcasBuffer<String> cToC; // iz njega vadim podatke
	SemAtomicBroadcasBuffer<String> cToP; // u njega upisujem na kraju
	ConcurrentHashMap<Integer, Double> osobe;
	boolean finished;
	int num;
	Semaphore writing = new Semaphore(1);
	
	public Combiner(SemAtomicBroadcasBuffer<String> c, SemAtomicBroadcasBuffer<String> c1, int numOfCust) {
		super("Combiner");
		this.cToC = c;
		this.cToP = c1;
		this.osobe = new ConcurrentHashMap<Integer,Double>();
		this.finished = false;
		this.num = numOfCust;
	}

	@Override
	public void run() {
		while(!finished) {
			String h = cToC.get(0);
			//System.out.println(h.substring(1, h.length()-1));
			if(h == null) 
				break;
			String str = h.substring(1, h.length()-1);
			String strData[] = str.split(",");
			for(int i = 0; i < strData.length; i++) {
				strData[i] = strData[i].replace(" ", "");
				String KV[] = strData[i].split("=");
				double kol = osobe.get(Integer.parseInt(KV[0])) == null ? 0 : osobe.get(Integer.parseInt(KV[0]));
				osobe.put(Integer.parseInt(KV[0]), kol + Double.parseDouble(KV[1]));
			}
		}
		cToP.put(osobe.toString());
		//System.out.println(osobe);
	}

}
