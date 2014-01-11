package client.exceptions;

public class ClienteNoDesconectadoException extends Exception {
	public ClienteNoDesconectadoException(){
		super("Cliente no desconectado de RMI");
	}
}
