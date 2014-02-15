package client.presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import client.controller.Controller;

public class RegisterDialog extends JDialog implements IRegistro {

	private static final long serialVersionUID = -5128501222928885944L;
	private JPanel registerPanel;
	private JButton CancelButton, AcceptButton;

	private JLabel mapLabel;

	private JLabel RepPasswdLabel;
	private JLabel EmailLabel;
	private JLabel PasswdLabel;
	private JLabel NoticeLabel;
	Border border = BorderFactory.createLineBorder(Color.BLACK);

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
		Controller cntrl;
		cntrl = Controller.get();
		cntrl.setRegistro(this);	
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
			"client/image/ok.png")));
		AcceptButton.setBounds(101, 232, 150, 35);
		AcceptButton.addMouseListener(new AcceptDialogMouseAdapter(this, true));

		CancelButton = new JButton("Cancelar");
		CancelButton.setIcon(new ImageIcon(
			this.getClass().getClassLoader().getResource(
			"client/image/cancel.png")));
		CancelButton.setBounds(271, 232, 150, 35);
		CancelButton.addMouseListener(new CancelDialogMouseAdapter(this, false));

		mapLabel = new JLabel();
		mapLabel.setIcon(new ImageIcon(
			this.getClass().getClassLoader().getResource("client/image/utt.png")));
		mapLabel.setBounds(0, 0, 491, 292);

		registerPanel.add(EmailLabel);
		registerPanel.add(EmailTextField);
		registerPanel.add(PasswdLabel);
		registerPanel.add(PasswdField);
		registerPanel.add(RepPasswdLabel);
		registerPanel.add(RepPasswdField);
		
		NoticeLabel = new JLabel();
		NoticeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		NoticeLabel.setFont(new Font("Dialog", Font.BOLD, 17));
		NoticeLabel.setText("");
		NoticeLabel.setBounds(92, 12, 300, 25);
		NoticeLabel.setForeground(Color.RED);
		registerPanel.add(NoticeLabel);
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

		public AcceptDialogMouseAdapter(RegisterDialog dlg, boolean selection) {
			this.dlg = dlg;
		}

		@Override
		public void mouseClicked(MouseEvent evt) {
			dlg.enviarDatosRegistro(dlg.getEmailTextField().getText(), dlg.getPasswdField().getText(), dlg.getRepPasswdField().getText());
		}
	}

	private class CancelDialogMouseAdapter extends MouseAdapter {

		private final RegisterDialog dlg;

		public CancelDialogMouseAdapter(RegisterDialog dlg, boolean selection) {
			this.dlg = dlg;
		}

		@Override
		public void mouseClicked(MouseEvent evt) {
			dlg.dispose();
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
				
				dlg.enviarDatosRegistro(dlg.getEmailTextField().getText(), dlg.getPasswdField().getText(), dlg.getRepPasswdField().getText());
			}
		}
	}
	
	public boolean enviarDatosRegistro(String email, String passwd, String passwdcheck) {
		boolean valido = false;
		if (passwd.equals(passwdcheck)) {
			Controller cntrl;
			cntrl = Controller.get();
			cntrl.enviarDatosRegistro(email, passwd);
			valido = true;
			
		} else {
			NoticeLabel.setText("Las contraseñas no coinciden");
			NoticeLabel.setForeground(new Color(200,0,0));
			NoticeLabel.setOpaque(true);
			NoticeLabel.setBorder(border);
			NoticeLabel.setVisible(true);
		}
		return valido;
	}
	
	public void excepcionRemota() {
		JOptionPane.showMessageDialog(this,"No se obtiene respuesta del servidor", "Error de red",JOptionPane.ERROR_MESSAGE);
		this.setVisible(false);
		
	}

	public void respuestaRegistro(boolean error) {
		if (error) {
			NoticeLabel.setText("El Usuario introducido ya existe");
			NoticeLabel.setForeground(new Color(200, 0, 0));
			NoticeLabel.setOpaque(true);
			NoticeLabel.setBorder(border);
			this.selection = false;
		} else {
			this.setVisible(false);
			this.selection = true;
		}
	}
}
