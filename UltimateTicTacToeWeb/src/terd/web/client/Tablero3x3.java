package terd.web.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiChild;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Image;

public class Tablero3x3 extends Composite {
	
	private Image[][]fields= new Image[3][3];
	private  String []urls = {"image/o.png", "image/x.png"};
	
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
	
	interface Tablero3x3UiBinder extends UiBinder<Widget, Tablero3x3> {
	}

	public Tablero3x3() {
		initWidget(uiBinder.createAndBindUi(this));
		fields[0][0]=i00;
		fields[0][1]=i01;
		fields[0][2]=i02;
		fields[1][0]=i10;
		fields[1][1]=i11;
		fields[1][2]=i12;
		fields[2][0]=i20;
		fields[2][1]=i21;
		fields[2][2]=i22;
		
		for (int i=0;i<3;i++){
			for( int j=0;j<3;j++){
//				fields[i][j] = new Image(this.urls[0]);
				fields[i][j].setVisible(true);
			}
		}
	}

	public void update(int cC,int fC,int player) {
		System.out.println("Updating board: " + cC + " " + fC + " " + player);
		this.fields[cC][fC].setUrl(this.urls[player]);
//		this.fields[cC][fC].setVisible(false);
		
	}

}
