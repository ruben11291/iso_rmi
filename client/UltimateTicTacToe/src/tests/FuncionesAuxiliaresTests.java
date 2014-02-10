package tests;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;

import client.exceptions.JugadorNoExisteException;

public class FuncionesAuxiliaresTests {
	
	public static void borrarBD(){
		Broker broker;
		System.out.println("borrarBD");
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
