package rs.ac.bg.etf.kdp.lab2.net2;



public class TestP {

	public static void main(String[] args) {
		String host = "localhost";
		int port = 4001;
		MessageBox<Integer> buffer = new NetMessageBox<Integer>(host, port);

		Prod p = new Prod(buffer);
		p.start(); // p.run();
	
	}

}
