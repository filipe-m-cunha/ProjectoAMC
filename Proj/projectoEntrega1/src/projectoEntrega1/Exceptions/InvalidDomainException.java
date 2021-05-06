package projectoEntrega1.Exceptions;

public class InvalidDomainException extends Exception{

	private static final long serialVersionUID = 1L;

	public InvalidDomainException(String errorMessage) {
	        super(errorMessage);
	    }
}
