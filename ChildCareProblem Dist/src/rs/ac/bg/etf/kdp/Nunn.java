package rs.ac.bg.etf.kdp;

import java.rmi.RemoteException;

public class Nunn {

	public static void main(String[] args) {
		String host = "localhost";
		int port = 4002;
		try {
			RmiConn myC = new MyRmiConn(host, port);
			while (true) {

				myC.comeToJob();
				Thread.sleep((int) (3000 + Math.random() * 3000));
				myC.goHome();

			}
		} catch (RemoteException | InterruptedException e) {
			e.printStackTrace();
		}

	}

}
