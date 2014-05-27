package terd.web.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.HTMLPanel;

public class TableroGlobal extends Composite {
	
	private Image[][]fields= new Image[3][3];
	private static TableroGlobalUiBinder uiBinder = GWT
			.create(TableroGlobalUiBinder.class);
	@UiField Image gi00;
	@UiField Image gi01;
	@UiField Image gi02;
	@UiField Image gi10;
	@UiField Image gi11;
	@UiField Image gi12;
	@UiField Image gi20;
	@UiField Image gi21;
	@UiField Image gi22;


	interface TableroGlobalUiBinder extends UiBinder<Widget, TableroGlobal> {
	}

	public TableroGlobal() {
		initWidget(uiBinder.createAndBindUi(this));
		fields[0][0]=gi00;
		fields[0][1]=gi01;
		fields[0][2]=gi02;
		fields[1][0]=gi10;
		fields[1][1]=gi11;
		fields[1][2]=gi12;
		fields[2][0]=gi20;
		fields[2][1]=gi21;
		fields[2][2]=gi22;
			
		for (int i=0;i<3;i++){
			for( int j=0;j<3;j++){
				fields[i][j].setVisible(true);
			}
		}
	}
	
	public void setTableroGanado(int fila, int col, int jugador) {
		switch (jugador) {
		case -1:
			fields[fila][col].setUrl("image/OSym.png");
			break;
		case 0:
			fields[fila][col].setUrl("image/empate.png");
		case 1:
			fields[fila][col].setUrl("image/X2.png");
		}
	}
}

