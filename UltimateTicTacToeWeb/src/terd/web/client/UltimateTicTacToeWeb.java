package terd.web.client;

import java.util.Vector;

import terd.web.client.exceptions.CasillaOcupadaException;
import terd.web.client.exceptions.CoordenadasNoValidasException;
import terd.web.client.exceptions.JugadorNoExisteException;
import terd.web.client.exceptions.JugadorYaRegistradoException;
import terd.web.client.exceptions.MovimientoNoValidoException;
import terd.web.client.exceptions.NoEstaJugandoException;
import terd.web.client.exceptions.NoTienesElTurnoException;
import terd.web.client.exceptions.PartidaFinalizadaException;
import terd.web.client.exceptions.TableroEmpateException;
import terd.web.client.exceptions.TableroGanadoException;
import terd.web.client.Jugador;
import terd.web.client.Tablero9x9;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;

public class UltimateTicTacToeWeb implements EntryPoint {

	private String loginName, retado, oponente;
	private Jugador jugador;
	private Timer listaTimer, retosTimer, respuestaRetosTimer, tableroTimer;
	private Button loginButton, abandonarButton, cerrarButton,registrarButton;
	private TextBox emailText;
	private PasswordTextBox passwdText;
	private Tablero9x9 tablero;
	private TableroGlobal tableroGlobal;
	private ListBox listaJugadores;
	private Image logo, turno1, turno2;
	private TextBox emailRegistro;
	private PasswordTextBox passwdRegistro;
	private PasswordTextBox repPasswdRegistro;
	private Label listaJ, labelSesion, jugador1, jugador2;

	private final ServerAsync UTTTService = GWT.create(Server.class);

	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";
	

	
	private void creaPartida(String retador, String retado){
		Jugador j1 = new Jugador(retador,"");
		jugador1.setText(retador);
		jugador1.setVisible(true);
		Jugador j2 = new Jugador(retado,"");
		jugador2.setText(retado);
		jugador2.setVisible(true);
		turno1.setVisible(true);
		this.tablero.setJugadorA(j1);
		this.tablero.setJugadorB(j2);
		this.tablero.setJugadorConelTurno(j1);
		this.tablero = tablero;
		j1.setTablero(this.tablero);
		j2.setTablero(this.tablero);
		tableroGlobal.setVisible(true);
	}
	
	private void trasFinalizarPartida() {
		tableroGlobal.setVisible(false);
		tablero.setVisible(false);
		tablero.clear();
		jugador1.setVisible(false);
		jugador2.setVisible(false);
		turno1.setVisible(false);
		turno2.setVisible(false);
		abandonarButton.setVisible(false);
		listaJugadores.setVisible(true);
		listaJ.setVisible(true);
		oponente = "";
		retado = "";
		tableroTimer.cancel();
		retosTimer.scheduleRepeating(3000);
	}
	private void trasCerrarSesion() {
		tableroGlobal.setVisible(false);
		if (tableroTimer != null) tableroTimer.cancel();
		retosTimer.cancel();
		listaTimer.cancel();
		tablero.setVisible(false);
		tablero.clear();
		jugador1.setVisible(false);
		jugador2.setVisible(false);
		turno1.setVisible(false);
		turno2.setVisible(false);
		abandonarButton.setVisible(false);
		listaJugadores.setVisible(false);
		cerrarButton.setVisible(false);
		labelSesion.setVisible(false);
		loginButton.setVisible(true);
		registrarButton.setVisible(true);
		emailText.setVisible(true);
		emailRegistro.setVisible(true);
		passwdRegistro.setVisible(true);
		repPasswdRegistro.setVisible(true);
		logo.setVisible(true);
		listaJ.setVisible(false);
		passwdText.setVisible(true);
		loginName = "";
		oponente = "";
		retado= "";
	}
	
	private void trasRetoAceptado(String string) {
		if (retado.equals("")) {
			creaPartida(oponente, loginName);
		} else {
			creaPartida(loginName, retado);
			respuestaRetosTimer.cancel();
		}
		
		retado = "";
		retosTimer.cancel();
		tableroTimer.scheduleRepeating(2000);
		tablero.setVisible(true);
		abandonarButton.setVisible(true);
		listaJugadores.setVisible(false);
		listaJ.setVisible(false);
	}
	
	private void trasLogin() {
		loginName = emailText.getText();
		jugador = new Jugador(loginName, "");
		retado = oponente = "";
		listaJugadores.setVisible(true);
		loginButton.setVisible(false);
		emailText.setVisible(false);
		passwdText.setVisible(false);
		registrarButton.setVisible(false);
		emailRegistro.setVisible(false);
		passwdRegistro.setVisible(false);
		repPasswdRegistro.setVisible(false);
		logo.setVisible(false);
		listaJ.setVisible(true);
		cerrarButton.setVisible(true);
		labelSesion.setText("Bienvenido, " + loginName);
		labelSesion.setVisible(true);
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
					int fT, fC, cT, cC;
					fT = result.get(0);
					cT = result.get(1);
					fC = result.get(2);
					cC = result.get(3);
					try {
						if (loginName.equals(tablero.getJugadorA().getEmail())) {
							tablero.getJugadorB().poner(cT, fT, cC, fC);
						} else {
							tablero.getJugadorA().poner(cT, fT, cC, fC);
						}
						cambiarTurno();
					} catch (NoTienesElTurnoException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NoEstaJugandoException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (CoordenadasNoValidasException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (MovimientoNoValidoException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (PartidaFinalizadaException e) {
						if (e.getEmpate()) {
							tableroGlobal.setTableroGanado(fT, cT, 0);
						} else {
							if (loginName.equals(tablero.getJugadorA().getEmail())) {
								tableroGlobal.setTableroGanado(fT, cT, 1);
							} else {
								tableroGlobal.setTableroGanado(fT, cT, -1);
							}
						}
						Window.alert(e.getEmail() + " ha ganado la partida.");
						trasFinalizarPartida();
					} catch (CasillaOcupadaException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (TableroGanadoException e) {
						if (loginName.equals(tablero.getJugadorA().getEmail())) {
							tableroGlobal.setTableroGanado(fT, cT, 1);
						} else {
							tableroGlobal.setTableroGanado(fT, cT, -1);
						}
						cambiarTurno();
					} catch (TableroEmpateException e) {
						tableroGlobal.setTableroGanado(fT, cT, 0);
						cambiarTurno();
					}					
				}
					
			}
			
		});				
	}
	
	private void cambiarTurno() {
		if (turno1.isVisible()) {
			turno1.setVisible(false);
			turno2.setVisible(true);
		} else {
			turno1.setVisible(true);
			turno2.setVisible(false);
		}
	}
	
	private void refrescarRespuestaReto() {
		UTTTService.recibirRespuestaReto(loginName, new AsyncCallback<Integer>() {

			@Override
			public void onFailure(Throwable caught) {
//				System.out.println("Error al refrescar respuesta reto: " + caught);
			}

			@Override
			public void onSuccess(Integer result) {
				if (result != null) {
					if (result != -1) {
						oponente = retado;
						tablero.setIdTablero(result);
						tableroTimer = new Timer() {
							
							@Override
							public void run() {
								refrescarTablero();
							}
	
						};
//						tableroTimer.scheduleRepeating(2000);
						trasRetoAceptado("TuEresElRetador");
						
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
					if (respuesta) {
						oponente = r;
					}
					UTTTService.respuestaAPeticionDeReto(r, loginName, respuesta, new AsyncCallback<Integer>() {

						@Override
						public void onFailure(Throwable caught) {
							
						}

						@Override
						public void onSuccess(Integer result) {
							if (result != -1) {
								tablero.setIdTablero(result);
								tableroTimer = new Timer() {									
									@Override
									public void run() {
										refrescarTablero();
									}

								};
//								tableroTimer.scheduleRepeating(2000);
								trasRetoAceptado("TuEresElRetado");
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
	
	private void registrar(){
		if (emailRegistro.getText().isEmpty() 
				|| passwdRegistro.getText().equals("") 
				|| repPasswdRegistro.getText().equals("")){
			Window.alert("Totos los campos son obligatorios");
		}
		else{
		
			if ( ! passwdRegistro.getText().equals(repPasswdRegistro.getText())  ){
				Window.alert("Las contraseñas deben coincidir");
			}
			else{
			
			UTTTService.registrar(emailRegistro.getText(), passwdRegistro.getText(),
					new AsyncCallback() {

						@Override
						public void onFailure(Throwable caught) {
							try{
								throw caught;
							}
							catch (Throwable e) {
								Window.alert("El jugador ya está registrado");
						     }
														
						}

						@Override
						public void onSuccess(Object result) {
							Window.alert("Usuario registrado");							
						}
				
					});
			}
			
	     }
		
		
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
	
	public void enviarMovimiento(int cT, int fT, int cC, int fC) {
		UTTTService.poner(tablero.getIdTablero(), loginName, cT, fT, cC, fC, new AsyncCallback() {
			@Override
			public void onFailure(Throwable caught) {
				Window.alert(SERVER_ERROR);
			}

			@Override
			public void onSuccess(Object result) {
			
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
//		rootPanel.add(registrarButton);
		RootPanel.get("registerButton").add(registrarButton);
		loginButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
//				loginButton.setEnabled(true);
//				loginButton.setFocus(true);
				login();
			}
		});
		
		registrarButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
//				registrarButton.setFocus(true);
				registrar();
				
			}
		});
		
		abandonarButton = new Button("Abandonar partida");
		abandonarButton.setText("Abandonar");
		abandonarButton.setStyleName("myButton");
		RootPanel.get("abandonarButton").add(abandonarButton);
		abandonarButton.setSize("95px", "30px");
		abandonarButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				abandonarButton.setEnabled(false);
				abandonarPartida();
			}
		});
		abandonarButton.setVisible(false);
		
		cerrarButton = new Button("Cerrar sesión");
		cerrarButton.setStyleName("myButton");
		RootPanel.get("cerrarButton").add(cerrarButton);
		cerrarButton.setSize("142px", "30px");
		cerrarButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				cerrarSesion();
			}
		});
		cerrarButton.setVisible(false);
		
		emailRegistro = new TextBox();
		RootPanel.get("emailRegistro").add(emailRegistro);
//		rootPanel.add(emailRegistro);
		
		passwdRegistro = new PasswordTextBox();
//		rootPanel.add(passwdRegistro);
		RootPanel.get("passwordRegistro").add(passwdRegistro);
		
		repPasswdRegistro = new PasswordTextBox();
//		rootPanel.add(repPasswdRegistro, 382, 85);
		RootPanel.get("repPasswordRegistro").add(repPasswdRegistro);
		
		emailText = new TextBox();
		RootPanel.get("nameFieldContainer").add(emailText);
		
		passwdText = new PasswordTextBox();
		RootPanel.get("nameFieldPassContainer").add(passwdText);

		tableroGlobal = new TableroGlobal();
		RootPanel.get("boardResult").add(tableroGlobal);
		tableroGlobal.setVisible(false);
		
		tablero = new Tablero9x9();
		
//		rootPanel.add(tablero, 118, 47);
//		tablero.setSize("100px", "100px");
		RootPanel.get("boardGame").add(tablero);
		tablero.setVisible(false);
		
		tablero.getGrid().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				int cT, fT, cC, fC;
				Tablero3x3 [][] tablerillos = tablero.getTablerillos();
				HTMLTable.Cell tableroGrande = ((Grid) event.getSource()).getCellForEvent(event);
				HTMLTable.Cell tableroPequeno = (tablerillos[tableroGrande.getCellIndex()][tableroGrande.getRowIndex()].getGrid().getCellForEvent(event));
				fT = tableroGrande.getRowIndex();
				cT = tableroGrande.getCellIndex();
				fC = tableroPequeno.getRowIndex();
				cC = tableroPequeno.getCellIndex();
				try {
					if (loginName.equals(tablero.getJugadorA().getEmail())) {
						tablero.getJugadorA().poner(cT, fT, cC, fC);
					} else {
						tablero.getJugadorB().poner(cT, fT, cC, fC);
					}
					enviarMovimiento(cT, fT, cC, fC);
					cambiarTurno();
				} catch (NoTienesElTurnoException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoEstaJugandoException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (CoordenadasNoValidasException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (MovimientoNoValidoException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (PartidaFinalizadaException e) {
					if (e.getEmpate()) {
						tableroGlobal.setTableroGanado(fT, cT, 0);
					} else {
						if (loginName.equals(tablero.getJugadorA().getEmail())) {
							tableroGlobal.setTableroGanado(fT, cT, -1);
						} else {
							tableroGlobal.setTableroGanado(fT, cT, 1);
						}
					}
					enviarMovimiento(cT, fT, cC, fC);
					Window.alert("Enhorabuena, has ganado la partida.");
					trasFinalizarPartida();
				} catch (CasillaOcupadaException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (TableroGanadoException e) {
					if (loginName.equals(tablero.getJugadorA().getEmail())) {
						tableroGlobal.setTableroGanado(fT, cT, -1);
					} else {
						tableroGlobal.setTableroGanado(fT, cT, 1);
					}
					enviarMovimiento(cT, fT, cC, fC);
					cambiarTurno();
				} catch (TableroEmpateException e) {
					tableroGlobal.setTableroGanado(fT, cT, 0);
					enviarMovimiento(cT, fT, cC, fC);
					cambiarTurno();
				}
			}
		});
		 
		 listaJugadores = new ListBox();
		 listaJugadores.addDoubleClickHandler(new DoubleClickHandler() {
		 	public void onDoubleClick(DoubleClickEvent event) {
		 		retado = listaJugadores.getItemText(listaJugadores.getSelectedIndex());
		 		retarJugador();
		 	}
		 });
		 
//		rootPanel.add(listaJugadores, 392, 104);
		 RootPanel.get("listaJugadores").add(listaJugadores);
		 listaJugadores.setSize("339px", "362px");
		 listaJugadores.setVisibleItemCount(5);
		 listaJugadores.setStyleName("lista");
		 listaJugadores.setVisible(false);
		
		 logo = new Image("image/unnamed.png");
//		rootPanel.add(image, 307, 88);
		RootPanel.get("logo").add(logo);
		logo.setSize("339px", "341px");
		
		listaJ = new Label("Lista de Jugadores");
//		rootPanel.add(listaJ, 622, 675);
		RootPanel.get("labelLista").add(listaJ);
		listaJ.setStyleName("labelLista");
		listaJ.setVisible(false);

		labelSesion = new Label("");
		labelSesion.setStyleName("labelSesion");
		RootPanel.get("labelSesion").add(labelSesion);
		labelSesion.setVisible(false);

		jugador1 = new Label("ANTONIO");
		jugador1.setStyleName("labelSesion");
		RootPanel.get("labelJugador1").add(jugador1);
		jugador1.setVisible(false);
		
		jugador2 = new Label("ANGEL");
		jugador2.setStyleName("labelSesion");
		RootPanel.get("labelJugador2").add(jugador2);
		jugador2.setVisible(false);
		
		turno1 = new Image("image/arrow.png");
		RootPanel.get("turno1").add(turno1);
		turno1.setVisible(false);
		
		turno2 = new Image("image/arrow.png");
		RootPanel.get("turno2").add(turno2);
		turno2.setVisible(false);
	}
}	

