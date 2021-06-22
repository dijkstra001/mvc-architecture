package database;

public class dbException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public dbException(String message) {
		super(message);
	}
}
