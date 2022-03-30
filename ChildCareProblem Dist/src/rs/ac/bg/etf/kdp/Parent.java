package rs.ac.bg.etf.kdp;

public class Parent {

	public static void main(String[] args) {
		String host = "localhost";
		int port = 4001;
		Conn c = new MyConn(host, port);
		while(true) {
			int numOfChildren = (int)(1 + Math.random()*2);
			// dovodi decu, socketima
			int ticket = c.bringChildren(numOfChildren);
			System.out.println("Parent" + ticket + " brought " + numOfChildren + " children.");
			try {
				Thread.sleep((int)(2000 + Math.random()*3000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//odvodi decu, socketima
			c.takeChildren(ticket);
			System.out.println("Parent" + ticket + " took " + numOfChildren + " children.");
		}
	}

}
