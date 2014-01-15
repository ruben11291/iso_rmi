package esi.uclm.iso.ultimate_tttoe.persistencia;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import esi.uclm.iso.ultimate_tttoe.dominio.*;

public class DAOJugador {

	public static void insert(Jugador jugador) throws ClassNotFoundException, SQLException{
		//Broker br = Broker.get();
		
		Connection bd = Broker.getBD();
		if(bd == null)System.out.print("asco");
		CallableStatement cs = bd.prepareCall("{call insert_player(?)}");
		cs.setString(1, jugador.getEmail());
		
		cs.executeUpdate();
		bd.close();		System.out.println(cs.toString());
		System.out.println("AAAAAAAAA");
	}
	
	public static void delete(Jugador jugador) throws ClassNotFoundException, SQLException{
		
		Connection bd = Broker.getBD();
		CallableStatement cs = bd.prepareCall("{call delete_player(?)}"); // crear procedimiento en la bd
		
		cs.setString(1, jugador.getEmail());
		cs.executeUpdate();
		bd.close();
		
	}

	public static void poner(Jugador jugador, int idTablero, int columna,
			int fila) {
		// TODO Auto-generated method stub
		
	}
}
