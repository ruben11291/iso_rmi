package client.presentation;

public interface IJuego {
	public void ponerFicha(String email, int cT, int fT, int cC, int fC);
	public void partidaFinalizada(String email);
	public void cerrar();
	public void cerrarPorAbandonoOponente();
	public void cambiarTurno();
	public void tableroGanado(String email, int col, int fila);
	public void tableroEmpatado(int col, int fila);
	public void excepcionRemota();
}
