package rs.ac.bg.etf;

import java.io.File;
import java.rmi.Remote;

public interface Pot extends Remote{

	public void cook(File f);
	
	public File eat();
}
