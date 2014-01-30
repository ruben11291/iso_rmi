package server.tests;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;

import client.exceptions.JugadorNoExisteException;
import server.domain.FTERD;
import server.domain.Jugador;
import server.domain.Tablero9x9;
import server.persistence.Broker;

public class FuncionesAuxiliaresTests {
	
	
	public static void crearEInsertarJugador(String jugador) throws ClassNotFoundException, SQLException{
		Jugador j = new Jugador(jugador);
		j.insert();
	}
	
	public static void autenticar(String email, String passwd) throws ClassNotFoundException, SQLException{
		FTERD fachada = FTERD.get();
		try {
			//fachada.autenticar(email,passwd);
			fachada.add(email,passwd);
		} catch (JugadorNoExisteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static int getIdPartida(FTERD fachada){
		
		Hashtable<Integer, Tablero9x9> tableros = fachada.getTableros();
		Collection c = tableros.values();
		Iterator it  = c.iterator();
		Tablero9x9 partida = (Tablero9x9)it.next();
		return  partida.getId();
		
		
		
	}
	
	public static void borrarBD(){
		Broker broker;
		try {
			broker = Broker.get();
			Connection bd=broker.getBD();
			
			String dm = "delete from Movimiento;";
			String dj = "delete from Jugador;";
			String dc = "delete from Casilla;";
			String dt3= "delete from Tablero3x3;";
			String dt9= "delete from Tablero9x9;";
			
			Statement st = bd.createStatement();
			st.executeUpdate(dm);
			st.executeUpdate(dj);
			st.executeUpdate(dc);
			st.executeUpdate(dt3);
			st.executeUpdate(dt9);
			
			bd.close();
		} catch (ClassNotFoundException | SQLException e) {
			e.toString();
		}
	}

}
