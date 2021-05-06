package projectoEntrega1.Exceptions;

public class InvalidSizeException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public InvalidSizeException(String errorMessage) {
	        super(errorMessage);
	    }
}
