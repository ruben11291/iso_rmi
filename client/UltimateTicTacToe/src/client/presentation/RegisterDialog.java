package client.presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.SwingConstants;

import client.controller.Controller;

public class RegisterDialog extends JDialog implements IRegistro {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5128501222928885944L;
	private JPanel registerPanel;
	private JButton CancelButton, AcceptButton;

	private JLabel mapLabel;

	private JLabel RepPasswdLabel;
	private JLabel EmailLabel;
	private JLabel PasswdLabel;

	private JTextField EmailTextField;
	public JTextField getEmailTextField() {
		return EmailTextField;
	}

	public JPasswordField getPasswdField() {
		return PasswdField;
	}

	public JPasswordField getRepPasswdField() {
		return RepPasswdField;
	}

	private JPasswordField PasswdField;
	private JPasswordField RepPasswdField;

	private boolean selection;

	public RegisterDialog(JFrame f, String string, boolean b) {
		super(f, string, b);
		this.initGUI();
	}

	private void initGUI() {
		this.setResizable(false);
		this.setSize(497, 319);

		registerPanel = new JPanel();
		this.getContentPane().add(registerPanel, BorderLayout.CENTER);
		registerPanel.setLayout(null);


		Border border = BorderFactory.createLineBorder(Color.BLACK);

		EmailLabel = new JLabel();
		EmailLabel.setForeground(new Color(128, 0, 0));
		EmailLabel.setHorizontalAlignment(SwingConstants.CENTER);
		EmailLabel.setText("Email");
		EmailLabel.setBounds(55, 87, 64, 24);
		EmailLabel.setOpaque(true);
		EmailLabel.setBorder(border);

		EmailTextField = new JTextField();
		EmailTextField.setBounds(170, 85, 295, 30);
		EmailTextField.setToolTipText("Introduzca aqui su correo electrónico");
		EmailTextField.addKeyListener(new AcceptDialogKeyAdapter(this));

		PasswdLabel = new JLabel();
		PasswdLabel.setForeground(new Color(128, 0, 0));
		PasswdLabel.setHorizontalAlignment(SwingConstants.CENTER);
		PasswdLabel.setText("Contraseña");
		PasswdLabel.setBounds(35, 133, 103, 24);
		PasswdLabel.setOpaque(true);
		PasswdLabel.setBorder(border);

		PasswdField = new JPasswordField();
		PasswdField.setBounds(170, 127, 295, 30);
		PasswdField.setToolTipText("Introduzca aqui su contraseña");
		PasswdField.addKeyListener(new AcceptDialogKeyAdapter(this));
		
		RepPasswdLabel = new JLabel();
		RepPasswdLabel.setForeground(new Color(128, 0, 0));
		RepPasswdLabel.setHorizontalAlignment(SwingConstants.CENTER);
		RepPasswdLabel.setText("Rep. Contraseña");
		RepPasswdLabel.setBounds(22, 177, 131, 24);
		RepPasswdLabel.setOpaque(true);
		RepPasswdLabel.setBorder(border);

		RepPasswdField = new JPasswordField();
		RepPasswdField.setBounds(170, 175, 295, 30);
		RepPasswdField.setToolTipText("Vuelva a repetir la contraseña");
		RepPasswdField.addKeyListener(new AcceptDialogKeyAdapter(this));

		AcceptButton = new JButton("Aceptar");
		AcceptButton.setIcon(new ImageIcon(
			this.getClass().getClassLoader().getResource(
			"image/ok.png")));
		AcceptButton.setBounds(101, 232, 150, 35);
		AcceptButton.addMouseListener(new AcceptDialogMouseAdapter(this, true));

		CancelButton = new JButton("Cancelar");
		CancelButton.setIcon(new ImageIcon(
			this.getClass().getClassLoader().getResource(
			"image/cancel.png")));
		CancelButton.setBounds(271, 232, 150, 35);
		CancelButton.addMouseListener(new AcceptDialogMouseAdapter(this, false));

		mapLabel = new JLabel();
		mapLabel.setIcon(new ImageIcon(
			this.getClass().getClassLoader().getResource("image/utt.png")));
		mapLabel.setBounds(0, 0, 491, 292);

		registerPanel.add(EmailLabel);
		registerPanel.add(EmailTextField);
		registerPanel.add(PasswdLabel);
		registerPanel.add(PasswdField);
		registerPanel.add(RepPasswdLabel);
		registerPanel.add(RepPasswdField);
		registerPanel.add(AcceptButton);
		registerPanel.add(CancelButton);
		registerPanel.add(mapLabel);
	}

	public boolean getSelection() {
		return selection;
	}

	public String getEmail() {
		return EmailTextField.getText();

	}

	@SuppressWarnings("deprecation")
	public String getPasswd() {
		return PasswdField.getText();
	}

	private class AcceptDialogMouseAdapter extends MouseAdapter {

		private final RegisterDialog dlg;
		private final boolean selection;

		public AcceptDialogMouseAdapter(RegisterDialog dlg, boolean selection) {
			this.dlg = dlg;
			this.selection = selection;
		}

		@Override
		public void mouseClicked(MouseEvent evt) {
			dlg.selection = selection;
			dlg.setVisible(false);
		}
	}

	private class AcceptDialogKeyAdapter extends KeyAdapter {
		private final RegisterDialog dlg;

		public AcceptDialogKeyAdapter(RegisterDialog dlg) {
			this.dlg = dlg;
		}

		@Override
		public void keyPressed(KeyEvent evt) {
			if (evt.getKeyCode() == 10) {
				dlg.selection = true;
				try {
					dlg.enviarDatosRegistro(dlg.getEmailTextField().getText(), dlg.getPasswdField().getText(), dlg.getRepPasswdField().getText());
				} catch (Exception e) {
					// TODO ANADIR LABEL DE ERROR EN REGISTRO
					e.printStackTrace();
				}				
				dlg.setVisible(false);
			} else if (evt.getKeyCode() == 27) {
				dlg.selection = false;
				dlg.setVisible(false);
			}
		}
	}
	
	public void enviarDatosRegistro(String email, String passwd, String passwdcheck) throws Exception {
		if (passwd.equals(passwdcheck)) {
			Controller cntrl = Controller.get();
			cntrl.enviarDatosRegistro(email, passwd);
		} else {
			//TODO label de repetir contraseña
		}
	}
	
	@Override
	public void recibirRespuesta() {
		// TODO Auto-generated method stub
		
	}
}
