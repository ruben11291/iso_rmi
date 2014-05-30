package terd.web;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Broker {
	private static Broker yo;
	
	private Broker() throws ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
	}
	
	public static Broker get() throws ClassNotFoundException {
		if (yo==null) {
			yo=new Broker();
		}
		return yo;
	}

	public Connection getBD() throws SQLException {
		return DriverManager.getConnection("jdbc:mysql://localhost:3306/bdiso", "root", "root");
	}
}
