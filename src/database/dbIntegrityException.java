package database;

public class dbIntegrityException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public dbIntegrityException(String msg) {
		super(msg);
	}
}
