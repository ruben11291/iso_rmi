package client.presentation;

public interface ILogin {
	public void recibirRespuestaLogin(boolean error);
	public void avisoCerrarSesion();
	public void jugadorNoExiste();
	public void jugadorYaExiste();
	public void excepcionRemota();
	public void mostrar();
}
