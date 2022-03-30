package rs.ac.bg.etf.kdp.lab2.net2;

@SuppressWarnings("serial")
public class BooleanStatus implements Status {

	private boolean s;
	
	public BooleanStatus(boolean b) {
		this.s = b;
	}

	@Override
	public void setStatus(boolean status) {
		this.s = status;
	}

	@Override
	public boolean getStatus() {
		return this.s;
	}

}
