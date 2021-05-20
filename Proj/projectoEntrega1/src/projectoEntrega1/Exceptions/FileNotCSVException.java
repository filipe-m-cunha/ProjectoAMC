package projectoEntrega1.Exceptions;

public class FileNotCSVException extends Exception{

	private static final long serialVersionUID = 1L;
	
	public FileNotCSVException(String errorMessage) {
		super(errorMessage);
	}
	
}
