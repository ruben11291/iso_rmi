package client.presentation;

public interface IJuego {
	public void ponerFicha(String email, int cT, int fT, int cC, int fC);
	public void hayGanador(int jugador);
}