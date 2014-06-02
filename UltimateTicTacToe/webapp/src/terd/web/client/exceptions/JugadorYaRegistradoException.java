package terd.web.client.exceptions;

public class JugadorYaRegistradoException extends Exception {
	public JugadorYaRegistradoException(String email){
		super(email);
	}
}
