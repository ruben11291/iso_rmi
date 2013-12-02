package esi.uclm.iso.ultimate_tttoe.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Broker {

	private static Broker broker;
	
	private Broker() {
		
	}
	
	public static Broker get() throws ClassNotFoundException, SQLException{
		if(broker == null)
			broker = new Broker();
		
		return broker;
	}

	public static Connection getBD() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		return DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root","root");
	}
	
}
