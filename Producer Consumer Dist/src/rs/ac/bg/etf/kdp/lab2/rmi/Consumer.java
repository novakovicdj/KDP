package rs.ac.bg.etf.kdp.lab2.rmi;

import rs.ac.bg.etf.kdp.lab2.Goods;

public class Consumer {

	public static void main(String[] args) throws Exception {
		String host = args[0];
		int port = Integer.parseInt(args[1]);
		int id = 2;//Integer.parseInt(args[2]);
		RmiABBuffer<Goods> ab = new RmiABBuffer<>(host, port); // TODO zameniti null stvaranjem konkretnog objekta, npr. new PCClass(...)


		for (int i = 3; i < args.length; i++) {
			String name = args[i];

			Goods goods = ab.get(name, id);
			System.out.println(name + ":");
			int size = goods.getNumLines();
			for (int j = 0; j < size; j++) {
				System.out.println(goods.readLine());
				Thread.sleep(1000 + (int) (Math.random() * 734));
			}
		}
		//ab.close();
	}
}
