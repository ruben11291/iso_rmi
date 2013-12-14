package server.persistence;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import server.domain.Casilla;
import server.domain.Jugador;
import server.domain.Tablero3x3;
import server.domain.Tablero9x9;

public class DAOTablero {
	
	public static boolean existeTablero(int id) throws ClassNotFoundException, SQLException{
		int idrs = -1;
		Broker broker=Broker.get();
		Connection bd=broker.getBD();
		
		boolean existe = false;
		String consulta = "SELECT idTablero FROM Tablero9x9 WHERE idTablero="+ Integer.toString(id);
		Statement st = bd.createStatement();
		ResultSet rs = st.executeQuery(consulta);
		
		if (rs != null && rs.next()){
			idrs = rs.getInt("idTablero");
			System.out.println(idrs);
			existe = true;
		}
		bd.close();
		return existe;
		
	}

	public static void nuevaPartida(Tablero9x9 tablero) throws SQLException, ClassNotFoundException {
		Broker broker=Broker.get();
		Connection bd=broker.getBD();
		CallableStatement cs=bd.prepareCall("{call insert_board(?)}");
		cs.setInt(1, tablero.getId());
		cs.executeUpdate();

		Tablero3x3[][] subTableros = tablero.getSubTableros();
		for(int fila=0; fila<3; fila++){
			for(int col=0; col<3; col++){
				nuevoSubTablero(tablero.getId(), subTableros[fila][col], fila, col, bd);				
			}
		}
		
		
		bd.close();
	}
	
	private static void nuevoSubTablero(int id9x9, Tablero3x3 subTablero, int fila, int col, Connection bd) throws SQLException{
		try{
			
			CallableStatement cs = bd.prepareCall("{call insert_sub_board(?,?,?,?)}");
			cs.setInt(1, subTablero.getId());
			cs.setInt(2, fila);
			cs.setInt(3, col);
			cs.setInt(4, id9x9);
			cs.executeUpdate();
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
		}
		
		Casilla[][] casillas = subTablero.getCasillas();
		for(int f=0; f<3; f++){
			for(int c=0; c<3; c++){
				nuevaCasilla(casillas[f][c], subTablero.getId(), f, c, bd);				
			}
		}
		
		
		
	}
	
	private static void nuevaCasilla(Casilla casilla, int idTablero, int fila, int col, Connection bd) throws SQLException{
		
		try{
			
			CallableStatement cs = bd.prepareCall("{call insert_cell(?,?,?,?)}");
			cs.setInt(1, casilla.getId());
			cs.setInt(2, fila);
			cs.setInt(3, col);
			cs.setInt(4, idTablero);
			cs.executeUpdate();
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
		}
		
	}
	
	public static void nuevoMovimiento(String email, int idCasilla, int valor) throws ClassNotFoundException, SQLException{
		Broker broker=Broker.get();
		Connection bd=broker.getBD();
		
		try{
			DAOTablero.nuevaFicha(idCasilla, valor, bd);
			
			int idMovimiento = Math.abs((new java.util.Random()).nextInt());
			CallableStatement cs=bd.prepareCall("{call insert_movement(?,?,?)}");
			cs.setInt(1, idMovimiento);
			cs.setInt(2, idCasilla);
			cs.setString(3, email);
			cs.executeUpdate();	
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
		}
		finally{
			bd.close();
		}
		
	}
	
	private static void nuevaFicha(int idCasilla, int valor, Connection bd) throws SQLException{
		CallableStatement cs=bd.prepareCall("{call insert_piece(?,?)}");
		cs.setInt(1, idCasilla);
		cs.setInt(2, valor);
		cs.executeUpdate();
	}
	
	/**
	 * Auxiliar: implementado para las pruebas de TestDAOMovimiento
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public static int consultarCasilla(String consulta) throws SQLException, ClassNotFoundException{
		Broker broker=Broker.get();
		Connection bd=broker.getBD();
		Statement st = (Statement) bd.createStatement();
		ResultSet rs = st.executeQuery(consulta);
		
		rs.next();
		int valor = rs.getInt(5);
		
		bd.close();
		return valor;
	}
	

}
