package terd.web.shared;

import java.util.Vector;

import com.google.gwt.user.client.rpc.IsSerializable;

public class WJugador implements IsSerializable {

	private String name;
	private String email;
	
	public void setEmail(String email) {
		this.email = email;
	}
	
//	private Vector <WTablero> tables;
//	public Vector <WTablero> getTables() {
//		return tables;
//	}
//	public void setTables(Vector <WTablero> tables) {
//		this.tables = tables;
//	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public WJugador(String name) {
		this.name = name;
	}
	public String getEmail() {
		return this.email;
	}
}
