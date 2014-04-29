package terd.web.shared;

import java.util.Vector;

public class WJugador {

	private String name;
	private Vector <WTablero> tables;
	public Vector <WTablero> getTables() {
		return tables;
	}
	public void setTables(Vector <WTablero> tables) {
		this.tables = tables;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
