package mn.spike.jwk.exception;

public class JwkException extends RuntimeException {
	public JwkException(String message) {
		super(message);
	}

	public JwkException(String message, Throwable cause) {
		super(message, cause);
	}
}
