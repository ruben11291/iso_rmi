package terd.web.client;

import java.util.Vector;

import terd.web.client.exceptions.JugadorNoExisteException;
import terd.web.client.exceptions.PartidaFinalizadaException;
import terd.web.client.exceptions.TableroEmpateException;
import terd.web.client.exceptions.TableroGanadoException;
import terd.web.shared.WJugador;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.event.dom.client.DoubleClickEvent;

public class UltimateTicTacToeWeb implements EntryPoint {

	private String loginName, retado, oponente;
	private WJugador me,j_retado,j_oponente;
	private Timer listaTimer, retosTimer, respuestaRetosTimer, tableroTimer;
	private Button loginButton, abandonarButton, cerrarButton,registrarButton;
	private TextBox emailText;
	private PasswordTextBox passwdText;
	private Tablero9x9 tablero;
	private ListBox listaJugadores;



	private final ServerAsync UTTTService = GWT.create(Server.class);
	private Button button;

	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";


	
	private void trasFinalizarPartida() {
		tablero.setVisible(false);
		tablero.clear();
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
		tablero.clear();
		abandonarButton.setVisible(false);
		listaJugadores.setVisible(false);
		cerrarButton.setVisible(false);
		loginButton.setVisible(true);
		registrarButton.setVisible(true);
		emailText.setVisible(true);
		passwdText.setVisible(true);
		loginName = "";
		me = null;
		j_oponente = null;
		j_retado = null;
		oponente = "";
		retado= "";
		System.out.println("Tras cerrar sesión");
	}
	
	private void trasRetoAceptado() {
		j_oponente = new WJugador(oponente,2);
	//	tablero = new Tablero9x9(this);
		
		tablero.setJugadorA(me);
		tablero.setJugadorB(j_oponente);
		tablero.setJugadorConelTurno(j_oponente);
		retado = "";
		j_retado = null;
		retosTimer.cancel();
		tableroTimer.scheduleRepeating(2000);
		tablero.setVisible(true);
		abandonarButton.setVisible(true);
		listaJugadores.setVisible(false);
		System.out.println("Tras reto aceptado");
	}
	
	private void trasLogin() {
		loginName = emailText.getText();
		this.me = new WJugador(loginName,1);
		j_oponente = j_retado = null;
		retado = oponente = "";
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
				Window.alert(SERVER_ERROR);
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
				
					
					try {
						tablero.colocar(result.get(0),result.get(1),result.get(2),result.get(3),j_oponente.getNum());
						Window.alert("COLOCADA");
					} catch (PartidaFinalizadaException e) {
						// TODO Auto-generated catch block
						Window.alert("Partida finalizada. Vencedor :"+j_oponente.getName());
						trasFinalizarPartida();
						e.printStackTrace();
					} catch (TableroGanadoException e) {
						// TODO Auto-generated catch block
						//Pintar en el tablero pequenyo
						e.printStackTrace();
					} catch (TableroEmpateException e) {
						Window.alert("Partida finalizada en empate");
						trasFinalizarPartida();
						e.printStackTrace();
					}
					
					
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
		registrarButton = new Button("Register");
		registrarButton.setStyleName("myButton");
//		RootPanel.get("menu_login").add(l);
		RootPanel rootPanel = RootPanel.get("sendButtonContainer");
		rootPanel.add(loginButton);
		rootPanel.add(registrarButton);
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

		tablero = new Tablero9x9(this);
		
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
		
		rootPanel.add(listaJugadores, 34, 156);
		listaJugadores.setSize("102px", "263px");
		listaJugadores.setVisibleItemCount(5);
		
		button = new Button("Log in");
		button.setText("Registrar");
		button.setStyleName("myButton");
		rootPanel.add(button, 0, 85);
		button.setSize("82px", "30px");
		
		listaJugadores.setVisible(false);

	}
	
	public void mostrar_msg_movimiento(String msg){
		Window.alert(msg);
	}
	public void notifica_movimiento(int cT, int fT, int cC, int fC) {
		//Hacer esta llamada. El servidor routea dependiendo del email no?
		//UTTTService.poner(0, me.getName(), cT, fT, cC, fC, callback);//REVISAR ID PARTIDA YA QUE NO ME HA DADO TIEMPO
		Window.alert("Se ha enviado el mov. a oponente. TODO");
		
	}
}	

