package terd.web.client;

import terd.web.shared.WJugador;


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
	private WJugador jugadorConElTurno;
	private WJugador jugadorA,jugadorB;
	private String vencedor;
	private int last_cT, last_fT, last_cC, last_fC;

	private Tablero3x3 [][] tables = new Tablero3x3 [3][3];
	
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
	UltimateTicTacToeWeb prx;

	interface Tablero9x9UiBinder extends UiBinder<Widget, Tablero9x9> {
	}

	public Tablero9x9(UltimateTicTacToeWeb ultimateTicTacToeWeb) {
		initWidget(uiBinder.createAndBindUi(this));
		tables[0][0]=t00;
		tables[0][1]=t01;
		tables[0][2]=t02;
		tables[1][0]=t10;
		tables[1][1]=t11;
		tables[1][2]=t12;
		tables[2][0]=t20;
		tables[2][1]=t21;
		tables[2][2]=t22;
		this.ultimoValor = 1;
		this.jugadorA = null;
		this.jugadorB = null;
		this.last_cT = this.last_fT = this.last_cC = this.last_fC = -1;
		this.vencedor = "";
		this.prx = ultimateTicTacToeWeb;
	}

	public int getIdTablero() {
		return idTablero;
	}

	public void setIdTablero(int idTablero) {
		this.idTablero = idTablero;
	}

	@UiHandler("grid")
	void onGridClick(ClickEvent event) {
		HTMLTable.Cell tableroGrande = ((Grid) event.getSource()).getCellForEvent(event);
		HTMLTable.Cell tableroPequeno = (tables[tableroGrande.getRowIndex()][tableroGrande.getCellIndex()].grid.getCellForEvent(event));
		poner_movimiento(tableroGrande.getCellIndex(),tableroGrande.getRowIndex(), tableroPequeno.getCellIndex(), tableroPequeno.getRowIndex());
	//	Window.alert(a.getCellIndex()+" "+a.getRowIndex()+ " "+b.getCellIndex()+" "+.getRowIndex());
		
	}
	
	private void poner_movimiento(int cT, int fT, int cC,
			int fC) {
		
//		try {
//			comprobarMovimiento(cT, fT, cC, fC);
//			colocar(cT, fT, cC, fC);
//		} catch (CoordenadasNoValidasException e) {
//			prx.mostrar_msg_movimiento(e.toString());
//			e.printStackTrace();
//		} catch (MovimientoNoValidoException e) {
//			prx.mostrar_msg_movimiento(e.toString());
//			e.printStackTrace();
//		} catch (CasillaOcupadaException e) {
//			
//			//e.printStackTrace();
//		} catch (PartidaFinalizadaException e) {
//			prx.mostrar_msg_movimiento(e.toString());
//			e.printStackTrace();
//		} catch (TableroGanadoException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (TableroEmpateException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		try{
			comprobarMovimiento(cT, fT, cC, fC);
			colocar(cT,fT,cC,fC);
		} catch (Exception e){
			
	}
		}
		
		
	}

	public WJugador getJugadorConElTurno() {
		return this.jugadorConElTurno;
	}
	
	public void setJugadorA(WJugador a) {
		this.jugadorA = a;
		setJugadorConelTurno(this.jugadorA);
	}
	
	public void setJugadorB(WJugador b) {
		this.jugadorB = b;
	}

	public WJugador getJugadorA() {
		return jugadorA;
	}

	public WJugador getJugadorB() {
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
				
				// Comprueba si hay alguna combinación ganadora vertical
				if ((this.tables[i][0].getVencedor().equals(a) || tables[i][0].getEmpate()) && 
						(this.tables[i][1].getVencedor().equals(a) || tables[i][1].getEmpate()) &&
						(this.tables[i][2].getVencedor().equals(a) || tables[i][2].getEmpate())) {
						vencedor = a;
				} else if ((this.tables[i][0].getVencedor().equals(b) || tables[i][0].getEmpate()) && 
						(this.tables[i][1].getVencedor().equals(b) || tables[i][1].getEmpate()) &&
						(this.tables[i][2].getVencedor().equals(b) || tables[i][2].getEmpate())) {
					vencedor = b;
					
				// Comprueba si hay alguna combinación ganadora horizontal
				} else if (this.tables[i][0].getEmpate() && this.tables[i][1].getEmpate() && this.tables[i][2].getEmpate()) {
					vencedor = this.getJugadorConElTurno().getEmail();
				} else if ((this.tables[0][i].getVencedor().equals(a) || tables[0][i].getEmpate()) && 
						(this.tables[1][i].getVencedor().equals(a) || tables[1][i].getEmpate()) &&
						(this.tables[2][i].getVencedor().equals(a) || tables[2][i].getEmpate())) {
					vencedor = a;
				} else if ((this.tables[0][i].getVencedor().equals(b) || tables[0][i].getEmpate()) && 
						(this.tables[1][i].getVencedor().equals(b) || tables[1][i].getEmpate()) &&
						(this.tables[2][i].getVencedor().equals(b) || tables[2][i].getEmpate())) {
					vencedor = b;
				} else if (this.tables[0][i].getEmpate() && this.tables[1][i].getEmpate() && this.tables[2][i].getEmpate()) {
					vencedor = this.getJugadorConElTurno().getEmail();
				}
			}
		}
				// Comprueba si hay alguna combinación ganadora diagonal
		if (vencedor.equals("")) {
			if ((this.tables[0][0].getVencedor().equals(a) || tables[0][0].getEmpate()) && 
					(this.tables[1][1].getVencedor().equals(a) || tables[1][1].getEmpate()) &&
					(this.tables[2][2].getVencedor().equals(a) || tables[2][2].getEmpate())) {
				vencedor = a;
			} else if ((this.tables[0][0].getVencedor().equals(b) || tables[0][0].getEmpate()) && 
					(this.tables[1][1].getVencedor().equals(b) || tables[1][1].getEmpate()) &&
					(this.tables[2][2].getVencedor().equals(b) || tables[2][2].getEmpate())) {
				vencedor = b;
			} else if (this.tables[0][0].getEmpate() && this.tables[1][1].getEmpate() && this.tables[2][2].getEmpate()) {
				vencedor = this.getJugadorConElTurno().getEmail();
			} else if ((this.tables[2][0].getVencedor().equals(a) || tables[2][0].getEmpate()) && 
					(this.tables[1][1].getVencedor().equals(a) || tables[1][1].getEmpate()) &&
					(this.tables[0][2].getVencedor().equals(a) || tables[0][2].getEmpate())) {
				vencedor = a;
			} else if ((this.tables[2][0].getVencedor().equals(b) || tables[2][0].getEmpate()) && 
					(this.tables[1][1].getVencedor().equals(b) || tables[1][1].getEmpate()) &&
					(this.tables[0][2].getVencedor().equals(b) || tables[0][2].getEmpate())) {
				vencedor = b;
			} else if (this.tables[2][0].getEmpate() && this.tables[1][1].getEmpate() && this.tables[0][2].getEmpate()) {
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
		if (this.tables[cT][fT].getCasillas()[cC][fC] != 0)
			throw new CasillaOcupadaException(cT, fT, cC, fC);
	
		if (this.last_fC != -1)
			if (this.last_cC != cT || this.last_fC != fT)
				if (!this.tables[this.last_cC][this.last_fC].isFull())
					throw new MovimientoNoValidoException(cT, fT, cC, fC);
	}
	
	public void colocar(int cT, int fT, int cC, int fC) throws PartidaFinalizadaException, TableroGanadoException, TableroEmpateException {
		Tablero3x3 tablerillo = this.tables[cT][fT];
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

	public void setJugadorConelTurno(WJugador jugador) {
		this.jugadorConElTurno = jugador;
	}
	
	public void cambiarTurno(){
		if (this.jugadorConElTurno.equals(this.jugadorA))
			this.jugadorConElTurno = this.jugadorB;
		else
			this.jugadorConElTurno = this.jugadorA;
	}

	
//	
//	void colocar(int fT,int cT,int fC,int cC, int player){
//		this.tables[fT][cT].colocar(fC,cC,player);
//	}
}
