package terd.web.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiChild;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.Grid;

public class Tablero3x3 extends Composite {
	
	private Image[][]fields= new Image[3][3];
	public Image[][] getFields() {
		return fields;
	}
	private int [][]casillas = new int[3][3];
	private  String []urls = {"image/x.png", "image/o.png"};
	private String vencedor;	
	private boolean empate;
	private String casillaVacia = "image/void.png";
	
	private static Tablero3x3UiBinder uiBinder = GWT
			.create(Tablero3x3UiBinder.class);
	@UiField Image i00;
	@UiField Image i01;
	@UiField Image i02;
	@UiField Image i10;
	@UiField Image i11;
	@UiField Image i12;
	@UiField Image i20;
	@UiField Image i21;
	@UiField Image i22;
	@UiField Grid grid;
	
	public Grid getGrid() {
		return grid;
	}
	
	interface Tablero3x3UiBinder extends UiBinder<Widget, Tablero3x3> {
	}

	public Tablero3x3() {
		initWidget(uiBinder.createAndBindUi(this));
		fields[0][0]=i00;
		fields[0][1]=i10;
		fields[0][2]=i20;
		fields[1][0]=i01;
		fields[1][1]=i11;
		fields[1][2]=i21;
		fields[2][0]=i02;
		fields[2][1]=i12;
		fields[2][2]=i22;
		
		for (int i=0;i<3;i++){
			for( int j=0;j<3;j++){
				fields[i][j].setVisible(true);
				this.casillas[i][j]=0;
			}
		}
		this.vencedor = "";	
		this.empate = false;

	}

	public void colocar(int cC, int fC, int player) {
		System.out.println("Updating board: " + cC + " " + fC + " " + player);
		if (player == -1)
			this.fields[cC][fC].setUrl(this.urls[0]);
		else
			this.fields[cC][fC].setUrl(this.urls[1]);
		this.casillas [cC][fC] = player;
	}

	
	public String getVencedor() {
		return this.vencedor;
	}
	
	private void setVencedor(String vencedor) {
		this.vencedor = vencedor;
	}

	public int [][] getCasillas(){
		return this.casillas;
	}

	public void setEmpate(boolean empate) {
		this.empate = empate;
		
	}
	public boolean getEmpate() {
		return this.empate;
		
	}

	public boolean isFull() {
		boolean isFull = true;
		for(int fila=0; fila<3; fila++){
			for(int col=0; col<3; col++){
				if (this.casillas[col][fila]== 0) {
					isFull = false;
					col = fila = 3;
				}
			}
		}
		return isFull;
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
			for (int i = 0; i < 3; i++) {
				
				// Comprueba si hay alguna combinaci��n ganadora vertical
				if (this.casillas[i][0] == this.casillas[i][1] 
					&& this.casillas[i][1] == this.casillas[i][2]
					&& this.casillas[i][2] != 0) {
					if (this.casillas[i][0] == 1)
						vencedor = a;
					else
						vencedor = b;
					i = 4;
					
				// Comprueba si hay alguna combinaci��n ganadora horizontal
				} else if (this.casillas[0][i] == this.casillas[1][i] 
					&& this.casillas[1][i] == this.casillas[2][i]
					&& this.casillas[2][i] != 0) {
					if (this.casillas[0][i] == 1)
						vencedor = a;
					else
						vencedor = b;
					i = 4;
				}
			}
			
			// Comprueba si hay alguna combinaci��n ganadora diagonal
			if (vencedor.equals("")) {
				if (this.casillas[0][0] == this.casillas[1][1]
					&& this.casillas[1][1]== this.casillas[2][2]
					&& this.casillas[2][2]!= 0) {
					if (this.casillas[2][2] == 1)
						vencedor = a;
					else
						vencedor = b;
				} else if (this.casillas[2][0] == this.casillas[1][1]
					&& this.casillas[1][1] == this.casillas[0][2]
					&& this.casillas[0][2] != 0) {
					if (this.casillas[0][2] == 1)
						vencedor = a;
					else
						vencedor = b;
				}
			}
		}
		setVencedor(vencedor);
	}
	public void clear() {
		for(int i=0;i<3;i++)
			for (int j=0;j<3;j++) {
				this.fields[i][j].setUrl(this.casillaVacia);
				this.casillas[i][j] = 0;
			}
		
		this.vencedor = "";	
		this.empate = false;
		
	}

	
//	
}
