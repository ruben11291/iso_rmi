package terd.web.client;

import terd.web.client.Jugador;
import terd.web.client.exceptions.*;
import terd.web.client.Tablero3x3;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.event.dom.client.ClickEvent;

public class Tablero9x9 extends Composite {
	
	private int idTablero;
	private int ultimoValor;
	private Jugador jugadorA,jugadorB, jugadorConElTurno;
	private String vencedor;
	private int last_cT, last_fT, last_cC, last_fC;

	private Tablero3x3 [][] tablerillos = new Tablero3x3 [3][3];

	public Tablero3x3[][] getTablerillos() {
		return tablerillos;
	}

	public void setTablerillos(Tablero3x3[][] tablerillos) {
		this.tablerillos = tablerillos;
	}

	private static Tablero9x9UiBinder uiBinder = GWT
			.create(Tablero9x9UiBinder.class);
	@UiField Tablero3x3 t00;
	@UiField Tablero3x3 t01;
	@UiField Tablero3x3 t02;
	@UiField Tablero3x3 t10;
	@UiField Tablero3x3 t11;
	@UiField Tablero3x3 t12;
	@UiField Tablero3x3 t20;
	@UiField Tablero3x3 t21;
	@UiField Tablero3x3 t22;
	@UiField Grid grid;
	public Grid getGrid() {
		return grid;
	}

	public void setGrid(Grid grid) {
		this.grid = grid;
	}

	interface Tablero9x9UiBinder extends UiBinder<Widget, Tablero9x9> {
	}

	public Tablero9x9()  {
		initWidget(uiBinder.createAndBindUi(this));
		tablerillos[0][0]=t00;
		tablerillos[0][1]=t10;
		tablerillos[0][2]=t20;
		tablerillos[1][0]=t01;
		tablerillos[1][1]=t11;
		tablerillos[1][2]=t21;
		tablerillos[2][0]=t02;
		tablerillos[2][1]=t12;
		tablerillos[2][2]=t22;
		this.ultimoValor = 1;
		this.jugadorA = null;
		this.jugadorB = null;
		this.last_cT = this.last_fT = this.last_cC = this.last_fC = -1;
		this.vencedor = "";
	}

	public int getIdTablero() {
		return idTablero;
	}

	public void setIdTablero(int idTablero) {
		this.idTablero = idTablero;
	}

//	@UiHandler("grid")
//	void onGridClick(ClickEvent event) {
//		HTMLTable.Cell tableroGrande = ((Grid) event.getSource()).getCellForEvent(event);
//		HTMLTable.Cell tableroPequeno = (tables[tableroGrande.getRowIndex()][tableroGrande.getCellIndex()].grid.getCellForEvent(event));
//		try {
//			poner_movimiento(tableroGrande.getRowIndex(),tableroGrande.getCellIndex(), tableroPequeno.getRowIndex(), tableroPequeno.getCellIndex());
//		} catch (NoTienesElTurnoException e) {
//			prx.mostrar_msg_movimiento("No tienes el turno");
//		}
//	//	Window.alert(a.getCellIndex()+" "+a.getRowIndex()+ " "+b.getCellIndex()+" "+.getRowIndex());
//		
//	}
	

	public Jugador getJugadorConElTurno() {
		return this.jugadorConElTurno;
	}
	
	public void setJugadorA(Jugador a) {
		this.jugadorA = a;
		setJugadorConelTurno(this.jugadorA);
	}
	
	public void setJugadorB(Jugador b) {
		this.jugadorB = b;
	}

	public Jugador getJugadorA() {
		return jugadorA;
	}

	public Jugador getJugadorB() {
		return jugadorB;
	}

	public boolean hayVencedor() {
		boolean hay = false;
		if (!this.vencedor.equals(""))
			hay = true;
		return hay;
	}

	public void comprobarVencedor(String a, String b) {
		String vencedor = "";
		
		if (!hayVencedor()) {
			for (int i = 0; i < 3 && vencedor.equals(""); i++) {
				
				// Comprueba si hay alguna combinaci��n ganadora vertical
				if ((this.tablerillos[i][0].getVencedor().equals(a) || tablerillos[i][0].getEmpate()) && 
						(this.tablerillos[i][1].getVencedor().equals(a) || tablerillos[i][1].getEmpate()) &&
						(this.tablerillos[i][2].getVencedor().equals(a) || tablerillos[i][2].getEmpate())) {
						vencedor = a;
				} else if ((this.tablerillos[i][0].getVencedor().equals(b) || tablerillos[i][0].getEmpate()) && 
						(this.tablerillos[i][1].getVencedor().equals(b) || tablerillos[i][1].getEmpate()) &&
						(this.tablerillos[i][2].getVencedor().equals(b) || tablerillos[i][2].getEmpate())) {
					vencedor = b;
					
				// Comprueba si hay alguna combinaci��n ganadora horizontal
				} else if (this.tablerillos[i][0].getEmpate() && this.tablerillos[i][1].getEmpate() && this.tablerillos[i][2].getEmpate()) {
					vencedor = this.getJugadorConElTurno().getEmail();
				} else if ((this.tablerillos[0][i].getVencedor().equals(a) || tablerillos[0][i].getEmpate()) && 
						(this.tablerillos[1][i].getVencedor().equals(a) || tablerillos[1][i].getEmpate()) &&
						(this.tablerillos[2][i].getVencedor().equals(a) || tablerillos[2][i].getEmpate())) {
					vencedor = a;
				} else if ((this.tablerillos[0][i].getVencedor().equals(b) || tablerillos[0][i].getEmpate()) && 
						(this.tablerillos[1][i].getVencedor().equals(b) || tablerillos[1][i].getEmpate()) &&
						(this.tablerillos[2][i].getVencedor().equals(b) || tablerillos[2][i].getEmpate())) {
					vencedor = b;
				} else if (this.tablerillos[0][i].getEmpate() && this.tablerillos[1][i].getEmpate() && this.tablerillos[2][i].getEmpate()) {
					vencedor = this.getJugadorConElTurno().getEmail();
				}
			}
		}
				// Comprueba si hay alguna combinaci��n ganadora diagonal
		if (vencedor.equals("")) {
			if ((this.tablerillos[0][0].getVencedor().equals(a) || tablerillos[0][0].getEmpate()) && 
					(this.tablerillos[1][1].getVencedor().equals(a) || tablerillos[1][1].getEmpate()) &&
					(this.tablerillos[2][2].getVencedor().equals(a) || tablerillos[2][2].getEmpate())) {
				vencedor = a;
			} else if ((this.tablerillos[0][0].getVencedor().equals(b) || tablerillos[0][0].getEmpate()) && 
					(this.tablerillos[1][1].getVencedor().equals(b) || tablerillos[1][1].getEmpate()) &&
					(this.tablerillos[2][2].getVencedor().equals(b) || tablerillos[2][2].getEmpate())) {
				vencedor = b;
			} else if (this.tablerillos[0][0].getEmpate() && this.tablerillos[1][1].getEmpate() && this.tablerillos[2][2].getEmpate()) {
				vencedor = this.getJugadorConElTurno().getEmail();
			} else if ((this.tablerillos[2][0].getVencedor().equals(a) || tablerillos[2][0].getEmpate()) && 
					(this.tablerillos[1][1].getVencedor().equals(a) || tablerillos[1][1].getEmpate()) &&
					(this.tablerillos[0][2].getVencedor().equals(a) || tablerillos[0][2].getEmpate())) {
				vencedor = a;
			} else if ((this.tablerillos[2][0].getVencedor().equals(b) || tablerillos[2][0].getEmpate()) && 
					(this.tablerillos[1][1].getVencedor().equals(b) || tablerillos[1][1].getEmpate()) &&
					(this.tablerillos[0][2].getVencedor().equals(b) || tablerillos[0][2].getEmpate())) {
				vencedor = b;
			} else if (this.tablerillos[2][0].getEmpate() && this.tablerillos[1][1].getEmpate() && this.tablerillos[0][2].getEmpate()) {
				vencedor = this.getJugadorConElTurno().getEmail();
			}	
		}
		setVencedor(vencedor);
	}
	
	public String getVencedor() {
		return this.vencedor;
	}
	
	private void setVencedor(String vencedor) {
		this.vencedor = vencedor;
	}
	
	public void comprobarMovimiento(int cT, int fT, int cC, int fC) throws CoordenadasNoValidasException, MovimientoNoValidoException, CasillaOcupadaException {

		if (cT<0 || cT>2 || fT<0 || fT>2 || cC<0 || cC>2 || fC<0 || fC>2)
			throw new CoordenadasNoValidasException(cT, fT, cC, fC);
		if (this.tablerillos[cT][fT].getCasillas()[cC][fC] != 0)
			throw new CasillaOcupadaException(cT, fT, cC, fC);
	
		if (this.last_fC != -1)
			if (this.last_cC != cT || this.last_fC != fT)
				if (!this.tablerillos[this.last_cC][this.last_fC].isFull())
					throw new MovimientoNoValidoException(cT, fT, cC, fC);
	}

	public void colocar(int cT, int fT, int cC, int fC) throws PartidaFinalizadaException, TableroGanadoException, TableroEmpateException {
		Tablero3x3 tablerillo = this.tablerillos[cT][fT];
		tablerillo.colocar(cC, fC, this.ultimoValor);
		if (tablerillo.getVencedor().equals(""))
			tablerillo.comprobarVencedor(this.jugadorA.getEmail(), this.jugadorB.getEmail());
		this.ultimoValor *= -1;
			
		this.last_cT = cT; this.last_fT = fT; this.last_cC = cC; this.last_fC = fC;
		this.cambiarTurno();
		
		if (!tablerillo.getVencedor().equals("")) {
			this.comprobarVencedor(this.jugadorA.getEmail(), this.jugadorB.getEmail());
			if (!this.vencedor.equals(""))
				throw new PartidaFinalizadaException(this.vencedor, cT, fT);
			throw new TableroGanadoException(tablerillo.getVencedor(), cT, fT);
		}
		if(tablerillo.getVencedor().equals("") && tablerillo.isFull()){
			tablerillo.setEmpate(true);
			this.comprobarVencedor(this.jugadorA.getEmail(), this.jugadorB.getEmail());
			if (!this.vencedor.equals(""))
				throw new PartidaFinalizadaException(this.vencedor, cT, fT, true);
			throw new TableroEmpateException(cT,fT);
		}
	}

	public void setJugadorConelTurno(Jugador jugador) {
		this.jugadorConElTurno = jugador;
	}
	
	public void cambiarTurno(){
		if (this.jugadorConElTurno.equals(this.jugadorA))
			this.jugadorConElTurno = this.jugadorB;
		else
			this.jugadorConElTurno = this.jugadorA;
	}

	public void clear() {
		for(int i =0;i<3;i++)
			for (int j=0;j<3;j++)
				this.tablerillos[i][j].clear();
		
	}

	
}
