package eu.supersede.fe.exception;

@SuppressWarnings("serial")
public class SupersedeRuntimeException extends RuntimeException{
	
	private String errorMessage;
	
	public SupersedeRuntimeException() {
		super();
	}
	
	public SupersedeRuntimeException(String message)
	{
		super(message);
		errorMessage = message;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
}
