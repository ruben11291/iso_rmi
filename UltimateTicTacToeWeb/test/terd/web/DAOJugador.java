package terd.web;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;

import terd.web.client.Jugador;


public class DAOJugador {

	public static void insert(Jugador jugador) throws ClassNotFoundException, SQLException {
		Broker broker=Broker.get();
		Connection bd=broker.getBD();
		CallableStatement cs=bd.prepareCall("{call insert_player(?,?)}");
		cs.setString(1, jugador.getEmail());
		cs.setString(2, jugador.getPasswd());
		cs.executeUpdate();
		bd.close();
	}
	
	public static void delete(Jugador jugador) throws ClassNotFoundException, SQLException{
		Broker broker=Broker.get();
		Connection bd=broker.getBD();
		CallableStatement cs = bd.prepareCall("{call delete_player(?)}");
		cs.setString(1, jugador.getEmail());
		cs.executeUpdate();
		bd.close();
		
	}
	
}
