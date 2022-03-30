package rs.ac.bg.etf.kdp.lab2.net2;

import java.io.Serializable;

public interface Status extends Serializable {
	public void setStatus(boolean status);

	public boolean getStatus();
}
