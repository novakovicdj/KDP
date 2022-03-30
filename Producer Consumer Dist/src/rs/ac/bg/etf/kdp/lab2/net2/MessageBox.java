package rs.ac.bg.etf.kdp.lab2.net2;

public interface MessageBox<T> {

	/**
	 * Inserts message to buffer, blocking if the buffer is full.
	 *
	 *
	 * @param msg          message to be put in buffer
	 * @param priority     priority of the message; lower number is higher priority
	 * @param timeToLiveMs number of milliseconds for which the message can be read;
	 *                     if more time passed, message is no longer valid; 0 means
	 *                     lives forever
	 */
	public void put(T msg, int priority, int timeToLiveMs);

	public T get(int timeToWait, Status status);
}
