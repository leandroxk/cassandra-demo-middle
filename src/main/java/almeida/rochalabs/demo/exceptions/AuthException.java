package almeida.rochalabs.demo.exceptions;

public class AuthException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public AuthException() {
		super("Email address or password are not correct");
	}
	
}
