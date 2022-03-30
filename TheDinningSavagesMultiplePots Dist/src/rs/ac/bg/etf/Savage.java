package rs.ac.bg.etf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class Savage {

	public static void main(String[] args) {
		String host = "localhost";
		int port = 4002;
		String[] food = { "Ham", "Eggs" };
		int i = 0;
		int iter = 0;
		Conn myConn = new NetConn(host, port);

		while (true) {
			File f = myConn.eat(food[i]);
			
			try (FileInputStream in = new FileInputStream(f);
					BufferedReader br = new BufferedReader(new InputStreamReader(in));) {
				String line;
				System.out.println(food[i] + iter);
				iter++;
				while ((line = br.readLine()) != null) {
					System.out.println(line);
				}
				System.out.println();
				i = (i + 1) % food.length;
				Thread.sleep((int) (500 + Math.random() * 500));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
