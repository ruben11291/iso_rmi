package terd.web.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.event.dom.client.ClickEvent;

public class Tablero9x9 extends Composite {
	
	private int idTablero;

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

	interface Tablero9x9UiBinder extends UiBinder<Widget, Tablero9x9> {
	}

	public Tablero9x9() {
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
	}

	public int getIdTablero() {
		return idTablero;
	}

	public void setIdTablero(int idTablero) {
		this.idTablero = idTablero;
	}

	@UiHandler("grid")
	void onGridClick(ClickEvent event) {
		System.out.println(event.getRelativeElement());
		System.out.println(event.getX());
		System.out.println(event.getY());
	}
	
	void update(int fT,int cT,int fC,int cC, int player){
		this.tables[fT][cT].update(fC,cC,player);
	}
}
