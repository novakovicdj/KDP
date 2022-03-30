package rs.ac.bg.etf.kdp.lab2;

public class Consumer {

	public static void main(String[] args) throws Exception {
		String host = args[0];
		int port = Integer.parseInt(args[1]);
		int id = 2;//Integer.parseInt(args[2]);
		AB ab = new NetAB(); // TODO zameniti null stvaranjem konkretnog objekta, npr. new PCClass(...)

		if (!ab.init(host, port))
			return;

		for (int i = 3; i < args.length; i++) {
			String name = args[i];

			Goods goods = ab.getGoods(name, id);

			int size = goods.getNumLines();
			for (int j = 0; j < size; j++) {
				System.out.println(goods.readLine());
				Thread.sleep(1000 + (int) (Math.random() * 734));
			}
		}
		ab.close();
	}
}
