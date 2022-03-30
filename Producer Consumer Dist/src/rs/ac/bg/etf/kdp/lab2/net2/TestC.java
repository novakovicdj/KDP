package rs.ac.bg.etf.kdp.lab2.net2;



public class TestC {

	public static void main(String[] args) {
		String host = "localhost";
		int port = 4001;
		MessageBox<Integer> buffer = new NetMessageBox<Integer>(host, port);

		Cons c = new Cons(buffer);
		c.start(); // p.run();
	}

}
