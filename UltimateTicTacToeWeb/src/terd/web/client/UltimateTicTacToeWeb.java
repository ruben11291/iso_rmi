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

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class UltimateTicTacToeWeb implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	
	private String login_name;
	private Timer t;
	
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final ServerAsync greetingService = GWT
			.create(Server.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		
		final Button btnNewButton = new Button("Log in");
		RootPanel rootPanel = RootPanel.get("sendButtonContainer");
		rootPanel.add(btnNewButton);
//		rootPanel.add(btnNewButton, 341, 120);
		
		final TextBox textBox = new TextBox();
		RootPanel.get("nameFieldContainer").add(textBox);
		
		
		final TextBox textBox_1 = new TextBox();
		RootPanel.get("nameFieldPassContainer").add(textBox_1);
		
		final ListBox listBox = new ListBox();
		listBox.addDoubleClickHandler(new DoubleClickHandler() {
			public void onDoubleClick(DoubleClickEvent event) {
				retarJugador();
			}
			
			private void retarJugador() {
			// Then, we send the input to the server.
		
			greetingService.retarJugador(login_name, listBox.getItemText(listBox.getSelectedIndex()),
					new AsyncCallback<Vector<String>>() {
						@Override
						public void onFailure(Throwable caught) {
							System.out.println("fallo: " + caught);
						}

						@Override
						public void onSuccess(Vector<String> result) {

						}
					});
			}
		});
		rootPanel.add(listBox, 10, 45);
		listBox.setSize("102px", "263px");
		listBox.setVisibleItemCount(5);
		listBox.setVisible(false);
		
//		rootPanel.add(textBox_1, 274, 70);

		// Create the popup dialog box
		final DialogBox dialogBox = new DialogBox();
		dialogBox.setText("Remote Procedure Call");
		dialogBox.setAnimationEnabled(true);
		final Button closeButton = new Button("Close");
		// We can set the id of a widget by accessing its Element
		closeButton.getElement().setId("closeButton");
		final Label textToServerLabel = new Label();
		final HTML serverResponseLabel = new HTML();
		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.add(new HTML("<b>Sending name to the server:</b>"));
		dialogVPanel.add(textToServerLabel);
		dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
		dialogVPanel.add(serverResponseLabel);
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		dialogVPanel.add(closeButton);
		dialogBox.setWidget(dialogVPanel);

		// Add a handler to close the DialogBox
		closeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				dialogBox.hide();
				btnNewButton.setEnabled(true);
				btnNewButton.setFocus(true);
			}
		});

		// Create a handler for the sendButton and nameField

		
		class LoginButtonHandler implements ClickHandler, KeyUpHandler{

			@Override
			public void onKeyUp(KeyUpEvent event) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onClick(ClickEvent event) {
				btnNewButton.setEnabled(true);
				btnNewButton.setFocus(true);
				login();
				
			}
			private void login() {
			// Then, we send the input to the server.
		
			greetingService.conectar(textBox.getText(), textBox_1.getText(),
					new AsyncCallback<Vector<String>>() {
						@Override
						public void onFailure(Throwable caught) {
							System.out.println("fallo: " + caught);
						}

						@Override
						public void onSuccess(Vector<String> result) {
							login_name = textBox.getText();
							listBox.setVisible(true);
							for (String r : result)
								listBox.addItem(r);
							t = new Timer() {
								
								@Override
								public void run() {
									// TODO Auto-generated method stub
									refrescarRetos();
									refrescarListaJugadores();
								}

							};
							t.scheduleRepeating(3000);
						}
					});
			}
			private void refrescarRetos() {
				// TODO Auto-generated method stub
				greetingService.getRetos(login_name, new AsyncCallback<Vector<String>>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						Window.alert("Error al refrescar retos: " + caught.toString());
					}

					@Override
					public void onSuccess(Vector<String> result) {
						// TODO Auto-generated method stub
						boolean respuesta = false;
						for (String r : result) {
							if (!respuesta)
								respuesta = Window.confirm(r + " te ha retado");
							
							greetingService.respuestaAPeticionDeReto(r, login_name, respuesta, -1, new AsyncCallback<Boolean>() {

								@Override
								public void onFailure(Throwable caught) {
									// TODO Auto-generated method stub
									
								}

								@Override
								public void onSuccess(Boolean result) {
									// TODO Auto-generated method stub
									
								}
							});
						}
					}
					
				});
			}
			
			private void refrescarListaJugadores() {
				greetingService.getListaJugadores(new AsyncCallback<Vector<String>>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						Window.alert("Error al refrescar lista de jugadores: " + caught.toString());
					}

					@Override
					public void onSuccess(Vector<String> result) {
						// TODO Auto-generated method stub
						listBox.clear();
						for (String r : result)
							listBox.addItem(r);
					}
					
				});				
			}
		}
		
		LoginButtonHandler handler_register_button = new LoginButtonHandler();
		btnNewButton.addClickHandler(handler_register_button);
	}
	}

