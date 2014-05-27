package terd.web.client;

import com.google.gwt.user.client.rpc.IsSerializable;

import terd.web.client.exceptions.CasillaOcupadaException;
import terd.web.client.exceptions.CoordenadasNoValidasException;
import terd.web.client.exceptions.MovimientoNoValidoException;
import terd.web.client.exceptions.NoEstaJugandoException;
import terd.web.client.exceptions.NoTienesElTurnoException;
import terd.web.client.exceptions.PartidaFinalizadaException;
import terd.web.client.exceptions.TableroEmpateException;
import terd.web.client.exceptions.TableroGanadoException;

public class Jugador implements IsSerializable {

	String email;
	String passwd;
	private Tablero9x9 tablero;
	
	public Jugador(String email, String passwd){
		this.email = email;
		this.passwd = passwd;
	}

	public void setTablero(Tablero9x9 tablero) {
		this.tablero = tablero;
	}

	public String getEmail() {
		return this.email;
	}
	
	public void poner(int cT, int fT, int cC, int fC) throws NoTienesElTurnoException, NoEstaJugandoException, CoordenadasNoValidasException, MovimientoNoValidoException, PartidaFinalizadaException, CasillaOcupadaException, TableroGanadoException, TableroEmpateException {
		if(this.tablero == null)
			throw new NoEstaJugandoException(email);
		
		if(this.tablero.getJugadorConElTurno() != this)
			throw new NoTienesElTurnoException();
		
		this.tablero.comprobarMovimiento(cT, fT, cC, fC);
		this.tablero.colocar(cT, fT, cC, fC);

	}
}
