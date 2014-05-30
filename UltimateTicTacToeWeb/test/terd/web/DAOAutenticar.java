package terd.web;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DAOAutenticar {
	
	public static boolean autenticar(String email,String passwd) throws ClassNotFoundException, SQLException{
		boolean existe = false;
		Broker broker=Broker.get();
		Connection bd=broker.getBD();
		
		String query = "SELECT * FROM Jugador where email='"+email + "' and passwd='"+passwd+"'";
		Statement st = (Statement) bd.createStatement();
		ResultSet rs = st.executeQuery(query);
		
		if (rs.next()){
			existe = true;
		}
		
		st.close();
		return existe;

	}

}
