package terd.web.client;

import java.util.Hashtable;
import java.util.Vector;

import terd.web.shared.FieldVerifier;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.DecoratedStackPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalSplitPanel;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.user.client.Command;

public class UltimateTicTacToeWeb implements EntryPoint {

	private String loginName, retado, oponente;
	private Timer listaTimer, retosTimer, respuestaRetosTimer, tableroTimer;
	private Button loginButton, abandonarButton, cerrarButton;
	private TextBox emailText;
	private PasswordTextBox passwdText;
	private Tablero9x9 tablero;
	private ListBox listaJugadores;




	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	private final ServerAsync UTTTService = GWT.create(Server.class);
	
	private void trasFinalizarPartida() {
		tablero.setVisible(false);
		abandonarButton.setVisible(false);
		listaJugadores.setVisible(true);
		oponente = "";
		retado = "";
		tableroTimer.cancel();
		retosTimer.scheduleRepeating(3000);
		System.out.println("Tras finalizar partida");
	}
	private void trasCerrarSesion() {
		tableroTimer.cancel();
		retosTimer.cancel();
		listaTimer.cancel();
		tablero.setVisible(false);
		abandonarButton.setVisible(false);
		listaJugadores.setVisible(false);
		cerrarButton.setVisible(false);
		loginButton.setVisible(true);
		emailText.setVisible(true);
		passwdText.setVisible(true);
		loginName = "";
		oponente = "";
		System.out.println("Tras cerrar sesión");
	}
	
	private void trasRetoAceptado() {
		retosTimer.cancel();
		tableroTimer.scheduleRepeating(2000);
		tablero.setVisible(true);
		abandonarButton.setVisible(true);
		listaJugadores.setVisible(false);
		retado = "";
		System.out.println("Tras reto aceptado");
	}
	
	private void trasLogin() {
		loginName = emailText.getText();
		listaJugadores.setVisible(true);
		loginButton.setVisible(false);
		emailText.setVisible(false);
		passwdText.setVisible(false);
		cerrarButton.setVisible(true);
		retosTimer.scheduleRepeating(3000);
		listaTimer.scheduleRepeating(3000);
		System.out.println("Tras login");
	}
	
	private void abandonarPartida() {
		UTTTService.abandonarPartida(loginName, new AsyncCallback() {

			@Override
			public void onFailure(Throwable caught) {
				
			}
			
			@Override
			public void onSuccess(Object result) {
				
			}
		});
		trasFinalizarPartida();
	}
	
	private void cerrarSesion() {
		UTTTService.cerrarSesion(loginName, new AsyncCallback() {

			@Override
			public void onFailure(Throwable caught) {
				
			}

			@Override
			public void onSuccess(Object result) {
				
			}
		});
		trasCerrarSesion();
	}
	
	private void refrescarTablero() {
		UTTTService.getMovimiento(oponente, new AsyncCallback<Vector<Integer>>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error al refrescar tablero: " + caught.toString());
			}

			@Override
			public void onSuccess(Vector<Integer> result) {
				
				switch (result.get(0)) {
				case -1:
					break;
				case -2:
					Window.alert("Oponente ha abandonado.");
					trasFinalizarPartida();
					break;
				default:
					System.out.println("fT: " + result.get(0));
					System.out.println("fC: " + result.get(1));
					System.out.println("cT: " + result.get(2));
					System.out.println("cC: " + result.get(3));
					tablero.update(result.get(0),result.get(1),result.get(2),result.get(3),1);//hay que decidir que elemento va a ser quien
						
				}
					
			}
			
		});				
	}
	
	private void refrescarRespuestaReto() {
		UTTTService.recibirRespuestaReto(loginName, new AsyncCallback<Boolean>() {

			@Override
			public void onFailure(Throwable caught) {
				System.out.println("Error al refrescar respuesta reto: " + caught);
			}

			@Override
			public void onSuccess(Boolean result) {
				if (result != null) {
					if (result) {
						oponente = retado;
						tableroTimer = new Timer() {
							
							@Override
							public void run() {
								refrescarTablero();
							}
	
						};
//						tableroTimer.scheduleRepeating(2000);
						trasRetoAceptado();
						
					} else {
						Window.alert(retado + " ha rechazado tu reto.");
					}
				}
			}
			
		});
		
	}
	
	private void refrescarRetos() {
		UTTTService.getRetos(loginName, new AsyncCallback<Vector<String>>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error al refrescar retos: " + caught.toString());
			}

			@Override
			public void onSuccess(Vector<String> result) {
				boolean respuesta = false;
				for (String r : result) {
					if (!respuesta)
						respuesta = Window.confirm(r + " te ha retado");
					if (respuesta) oponente = r;
					UTTTService.respuestaAPeticionDeReto(r, loginName, respuesta, -1, new AsyncCallback<Boolean>() {

						@Override
						public void onFailure(Throwable caught) {
							
						}

						@Override
						public void onSuccess(Boolean result) {
							if (result) {
								tableroTimer = new Timer() {									
									@Override
									public void run() {
										refrescarTablero();
									}

								};
//								tableroTimer.scheduleRepeating(2000);
								trasRetoAceptado();
							}
						}
					});
				}
			}
			
		});
	}
	
	private void refrescarListaJugadores() {
		UTTTService.getListaJugadores(new AsyncCallback<Vector<String>>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error al refrescar lista de jugadores: " + caught.toString());
			}

			@Override
			public void onSuccess(Vector<String> result) {
				listaJugadores.clear();
				for (String r : result)
					listaJugadores.addItem(r);
			}
			
		});				
	}
	
	private void login() {
		UTTTService.conectar(emailText.getText(), passwdText.getText(),
			new AsyncCallback<Vector<String>>() {
				@Override
				public void onFailure(Throwable caught) {
					System.out.println("Error al hacer login: " + caught);
				}

				@Override
				public void onSuccess(Vector<String> result) {
//					listaJugadores.clear();
//					for (String r : result)
//						listaJugadores.addItem(r);
					listaTimer = new Timer() {
						
						@Override
						public void run() {
							refrescarListaJugadores();
						}

					};
//					listaTimer.scheduleRepeating(3000);
					retosTimer = new Timer() {

						@Override
						public void run() {
							refrescarRetos();
						}
						
					};
//					retosTimer.scheduleRepeating(3000);
					trasLogin();
				}
			});
	}
	
	private void retarJugador() {
		UTTTService.retarJugador(loginName, retado,
			new AsyncCallback<Vector<String>>() {
				@Override
				public void onFailure(Throwable caught) {
					System.out.println("Error al retar jugador: " + caught);
				}

				@Override
				public void onSuccess(Vector<String> result) {
					respuestaRetosTimer = new Timer() {
						
						@Override
						public void run() {
							refrescarRespuestaReto();
						}

					};
					respuestaRetosTimer.scheduleRepeating(1000);
				}
			});
	}
	
	public void onModuleLoad() {
		
		//Login l = new Login();		
		loginButton = new Button("Log in");
		loginButton.setStyleName("myButton");

//		RootPanel.get("menu_login").add(l);
		RootPanel rootPanel = RootPanel.get("sendButtonContainer");
		rootPanel.add(loginButton);
		loginButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				loginButton.setEnabled(true);
				loginButton.setFocus(true);
				login();
			}
		});
		
		abandonarButton = new Button("Abandonar partida");
		abandonarButton.setStyleName("myButton");
		
		rootPanel.add(abandonarButton, 10, 327);
		abandonarButton.setSize("100px", "49px");
		abandonarButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				abandonarButton.setEnabled(false);
				abandonarPartida();
			}
		});
		abandonarButton.setVisible(false);
		
		cerrarButton = new Button("Cerrar sesión");
		rootPanel.add(cerrarButton, 679, 10);
		cerrarButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				cerrarButton.setEnabled(false);
				cerrarSesion();
			}
		});
		cerrarButton.setVisible(false);
		
		emailText = new TextBox();
		RootPanel.get("nameFieldContainer").add(emailText);
		
		passwdText = new PasswordTextBox();
		RootPanel.get("nameFieldPassContainer").add(passwdText);

		tablero = new Tablero9x9();
//		rootPanel.add(tablero, 118, 47);
//		tablero.setSize("100px", "100px");
		RootPanel.get("boardGame").add(tablero);
		tablero.setVisible(false);
		
		listaJugadores = new ListBox();
		listaJugadores.addDoubleClickHandler(new DoubleClickHandler() {
			public void onDoubleClick(DoubleClickEvent event) {
				retado = listaJugadores.getItemText(listaJugadores.getSelectedIndex());
				retarJugador();
			}
		});
		
		rootPanel.add(listaJugadores, 10, 45);
		listaJugadores.setSize("102px", "263px");
		listaJugadores.setVisibleItemCount(5);
		
		listaJugadores.setVisible(false);

	}
	}

