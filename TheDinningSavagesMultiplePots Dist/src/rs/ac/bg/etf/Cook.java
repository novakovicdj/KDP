package rs.ac.bg.etf;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Cook {

	public static void main(String[] args) {
		String host = "localhost";
		int port = 4001;
		String[] food = { "Apple", "Eggs", "Ham" };
		int i = 0;
		int iter = 0;

		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
		try {
			Registry reg = LocateRegistry.getRegistry(host, port);

			TogetherPot p = (TogetherPot) reg.lookup("/TogetherPot");

			while (true) {
				File file = new File(food[i]);
				try (FileOutputStream out = new FileOutputStream(file);
						BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out))) {
					int num = (int) (Math.random() * 4 + 1);
					System.out.println(food[i] + iter);
					for (int m = 0; m < num; m++) {
						double item = Math.random() * 10 + 1;
						bw.write("" + item);
						bw.newLine();
						System.out.println("" + item);
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				p.cook(food[i], file);
				i = (i + 1) % food.length;
				iter++;
				Thread.sleep((int) (Math.random() * 500 + 700));
			}
		} catch (RemoteException | NotBoundException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
