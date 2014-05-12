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

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class UltimateTicTacToeWeb implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
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
//		final TextBox login_email = TextBox.wrap(Document.get().getElementById("login_email"));
//		final TextBox login_passwd = TextBox.wrap(Document.get().getElementById("login_passwd"));
//		final Element username = DOM.getElementById("login_email");
//		final Element password = DOM.getElementById("login_passwd");
//		final TextBox login_email = (username == null ? new TextBox() : TextBox.wrap(username));
//		final PasswordTextBox login_passwd = (password == null ? new PasswordTextBox() : PasswordTextBox.wrap(password));
		final Button register_button = Button.wrap(Document.get().getElementById("register_button"));
		final Button login_button = Button.wrap(Document.get().getElementById("login_button"));
		
		class LoginButtonHandler implements ClickHandler, KeyUpHandler{

			@Override
			public void onKeyUp(KeyUpEvent event) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onClick(ClickEvent event) {
				sendNameToServer();
				
			}
			private void sendNameToServer() {
			// Then, we send the input to the server.
			register_button.setEnabled(false);
			greetingService.conectar("angel", "angel",
					new AsyncCallback<Vector<String>>() {
						@Override
						public void onFailure(Throwable caught) {
							System.out.println("fallo: " + caught);
						}

						@Override
						public void onSuccess(Vector<String> result) {
							System.out.println(result);
						}
					});
			}
		}	
		
		
		
		LoginButtonHandler handler_register_button = new LoginButtonHandler();
		login_button.addClickHandler(handler_register_button);
		
		
		
		/*final Button sendButton = new Button("Send");
		final TextBox nameField = new TextBox();
		nameField.setText("GWT User");
		final Label errorLabel = new Label();

		// We can add style names to widgets
		sendButton.addStyleName("sendButton");

		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		RootPanel rootPanel = RootPanel.get("nameFieldContainer");
		rootPanel.setSize("800", "600");
		rootPanel.add(nameField, 10, 42);
		RootPanel.get("sendButtonContainer").add(sendButton, 380, 42);
		RootPanel.get("errorLabelContainer").add(errorLabel);

		// Focus the cursor on the name field when the app loads
		nameField.setFocus(true);
		
		HorizontalSplitPanel horizontalSplitPanel = new HorizontalSplitPanel();
		rootPanel.add(horizontalSplitPanel, 10, 105);
		horizontalSplitPanel.setSize("771px", "362px");
		
		ListBox listBox = new ListBox();
		horizontalSplitPanel.setLeftWidget(listBox);
		listBox.setSize("100%", "100%");
		listBox.setVisibleItemCount(5);
		
		TabPanel tabPanel = new TabPanel();
		horizontalSplitPanel.setRightWidget(tabPanel);
		tabPanel.setSize("100%", "100%");
		
		Tablero9x9 tablero9x9 = new Tablero9x9();
		tabPanel.add(tablero9x9, "New tab", false);
		tablero9x9.setSize("369px", "322px");
		
		PasswordTextBox passwordTextBox = new PasswordTextBox();
		rootPanel.add(passwordTextBox, 179, 42);
		
		MenuBar menuBar = new MenuBar(false);
		rootPanel.add(menuBar, 10, 10);
		MenuBar menuBar_1 = new MenuBar(true);
		
		MenuItem mntmOptions = new MenuItem("Options", false, menuBar_1);
		menuBar.addItem(mntmOptions);
		MenuBar menuBar_2 = new MenuBar(true);
		
		MenuItem mntmSalir = new MenuItem("Exit", false, menuBar_2);
		menuBar.addItem(mntmSalir);
		nameField.selectAll();

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
		dialogBox.setWidget(dialogVPanel);*/

		// Add a handler to close the DialogBox
//		closeButton.addClickHandler(new ClickHandler() {
//			public void onClick(ClickEvent event) {
//				dialogBox.hide();
//				sendButton.setEnabled(true);
//				sendButton.setFocus(true);
//			}
//		});

//		// Create a handler for the sendButton and nameField
//		class MyHandler implements ClickHandler, KeyUpHandler {
//			/**
//			 * Fired when the user clicks on the sendButton.
//			 */
//			public void onClick(ClickEvent event) {
//				sendNameToServer();
//			}
//
//			/**
//			 * Fired when the user types in the nameField.
//			 */
//			public void onKeyUp(KeyUpEvent event) {
//				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
//					sendNameToServer();
//				}
//			}
//
//			/**
//			 * Send the name from the nameField to the server and wait for a response.
//			 */
//			private void sendNameToServer() {
//				// First, we validate the input.
//				errorLabel.setText("");
//				String textToServer = nameField.getText();
//				if (!FieldVerifier.isValidName(textToServer)) {
//					errorLabel.setText("Please enter at least four characters");
//					return;
//				}
//
//				// Then, we send the input to the server.
//				sendButton.setEnabled(false);
//				textToServerLabel.setText(textToServer);
//				serverResponseLabel.setText("");
//				greetingService.greetServer(textToServer,
//						new AsyncCallback<String>() {
//							public void onFailure(Throwable caught) {
//								// Show the RPC error message to the user
//								dialogBox
//										.setText("Remote Procedure Call - Failure");
//								serverResponseLabel
//										.addStyleName("serverResponseLabelError");
//								serverResponseLabel.setHTML(SERVER_ERROR);
//								dialogBox.center();
//								closeButton.setFocus(true);
//							}
//
//							public void onSuccess(String result) {
//								dialogBox.setText("Remote Procedure Call");
//								serverResponseLabel
//										.removeStyleName("serverResponseLabelError");
//								serverResponseLabel.setHTML(result);
//								dialogBox.center();
//								closeButton.setFocus(true);
//							}
//						});
//			}
//		}
//
//		// Add a handler to send the name to the server
//		MyHandler handler = new MyHandler();
//		sendButton.addClickHandler(handler);
//		nameField.addKeyUpHandler(handler);
	}
}
