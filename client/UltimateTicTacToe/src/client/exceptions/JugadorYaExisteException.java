package client.exceptions;

public class JugadorYaExisteException extends Exception{
	public JugadorYaExisteException(String email){
		super(email);
	}
}
