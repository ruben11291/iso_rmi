package terd.web;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;


public class FuncionesAuxiliaresTests {
	
	public static void borrarBD(){
		Broker broker;
		System.out.println("borrarBD");
		try {
			broker = Broker.get();
			Connection bd=broker.getBD();
			
			String dm = "delete from Movimiento;";
			String dj = "delete from Jugador;";
			
			
			Statement st = bd.createStatement();
			st.executeUpdate(dm);
			st.executeUpdate(dj);
			
			
			bd.close();
		} catch (ClassNotFoundException | SQLException e) {
			e.toString();
		}
	}

}
