package esi.uclm.iso.ultimate_tttoe.persistencia;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

public class DAOAutenticar {
	
	public static boolean autenticar(String email) throws ClassNotFoundException, SQLException{
		boolean existe = false;
		
		Connection bd = Broker.getBD();
		System.out.println("1");
		String query = "SELECT * FROM Jugador where email='"+email + "'";
		Statement st = (Statement) bd.createStatement();
		ResultSet rs = st.executeQuery(query);
		
		if (rs.next()){
			existe = true;
		}
		
		st.close();
		return existe;

	}

}
