package server.persistence;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;
import server.persistence.Broker;

public class DAOAutenticar {
	
	public static boolean autenticar(String email) throws ClassNotFoundException, SQLException{
		boolean existe = false;
		Broker broker=Broker.get();
		Connection bd=broker.getBD();
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
