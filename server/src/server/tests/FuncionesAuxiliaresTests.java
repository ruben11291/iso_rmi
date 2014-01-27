package server.tests;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;

import server.domain.FTERD;
import server.domain.Jugador;
import server.domain.Tablero9x9;

public class FuncionesAuxiliaresTests {
	
	
	public static void crearEInsertarJugador(String jugador) throws ClassNotFoundException, SQLException{
		Jugador j = new Jugador(jugador);
		j.insert();
	}
	
	public static void autenticar(String email, String passwd) throws ClassNotFoundException, SQLException{
		FTERD fachada = FTERD.get();
		fachada.autenticar(email,passwd);
		
	}
	
	public static int getIdPartida(FTERD fachada){
		
		Hashtable<Integer, Tablero9x9> tableros = fachada.getTableros();
		Collection c = tableros.values();
		Iterator it  = c.iterator();
		Tablero9x9 partida = (Tablero9x9)it.next();
		return  partida.getId();
		
		
		
	}

}
