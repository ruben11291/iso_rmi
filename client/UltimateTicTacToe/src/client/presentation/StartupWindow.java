package client.presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

import client.controller.Controller;
import client.presentation.UltimateTicTacToe;

import javax.swing.SwingConstants;

import java.awt.Font;

public class StartupWindow extends JFrame implements ILogin {


	private static final long serialVersionUID = -5107198177153703399L;
	private JButton AcceptButton;
	private JButton RegisterButton;
	private JPanel StartupPanel;

	private JLabel mapLabel;
	private JLabel NoticeLabel;
	private JLabel EmailLabel;
	private JLabel PasswdLabel;

	private JTextField EmailTextField;
	public JTextField getEmailTextField() {
		return EmailTextField;
	}

	public JPasswordField getPasswdField() {
		return PasswdField;
	}

	private JPasswordField PasswdField;
	Border border = BorderFactory.createLineBorder(Color.BLACK);

	public StartupWindow() {
		super();
		Controller cntrl;
		cntrl = Controller.get();
		cntrl.setLogin(this);	
		this.initGUI();
	}

	private void initGUI() {
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		this.setResizable(false);
		this.setTitle("Ultimate Tic-Tac-Toe");
		this.setSize(461, 281);

		StartupPanel = new JPanel();
		this.getContentPane().add(StartupPanel, BorderLayout.CENTER);
		StartupPanel.setLayout(null);

		NoticeLabel = new JLabel();
		NoticeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		NoticeLabel.setFont(new Font("Dialog", Font.BOLD, 17));
		NoticeLabel.setText("");
		NoticeLabel.setBounds(90, 229, 300, 25);
		NoticeLabel.setForeground(Color.RED);

		EmailLabel = new JLabel();
		EmailLabel.setHorizontalAlignment(SwingConstants.CENTER);
		EmailLabel.setText("Login");
		EmailLabel.setBounds(42, 84, 81, 24);
		EmailLabel.setForeground(new Color(128, 0, 0));
		EmailLabel.setOpaque(true);
		EmailLabel.setBorder(border);
		
		EmailTextField = new JTextField();
		EmailTextField.setBounds(150, 82, 200, 30);
		EmailTextField.setToolTipText("Introduzca aqui su email o nombre de usuario");
		EmailTextField.addActionListener(new CreateSessionAction(this));

		PasswdLabel = new JLabel();
		PasswdLabel.setHorizontalAlignment(SwingConstants.CENTER);
		PasswdLabel.setText("Contraseña");
		PasswdLabel.setBounds(31, 130, 106, 25);
		PasswdLabel.setForeground(new Color(128,0,0));
		PasswdLabel.setOpaque(true);
		PasswdLabel.setBorder(border);

		PasswdField = new JPasswordField();
		PasswdField.setBounds(150, 124, 200, 30);
		PasswdField.setToolTipText("Introduzca aqui su contraseña");
		PasswdField.addActionListener(new CreateSessionAction(this));

		AcceptButton = new JButton("Aceptar");
		AcceptButton.setBounds(84, 183, 100, 30);
		AcceptButton.addActionListener(new CreateSessionAction(this));

		RegisterButton = new JButton("Registrarse");
		RegisterButton.setBounds(242, 187, 125, 30);
		RegisterButton.addMouseListener(new StartupRegisterMouseAdapter(this));

		mapLabel = new JLabel();
		mapLabel.setIcon(new ImageIcon(
			this.getClass().getClassLoader().getResource("image/utt.png")));
		mapLabel.setBounds(0, 0, 455, 254);

		StartupPanel.add(NoticeLabel);
		StartupPanel.add(EmailLabel);
		StartupPanel.add(EmailTextField);
		StartupPanel.add(PasswdLabel);
		StartupPanel.add(PasswdField);
		StartupPanel.add(AcceptButton);
		StartupPanel.add(RegisterButton);
		StartupPanel.add(mapLabel);
		this.setLocationRelativeTo(null);

	}

	private String getEmail() {
		return EmailTextField.getText();

	}


	private class StartupRegisterMouseAdapter extends MouseAdapter {

		private final StartupWindow stw;

		public StartupRegisterMouseAdapter(StartupWindow stw) {
			this.stw = stw;
		}

		@Override
		public void mouseClicked(MouseEvent evt) {
			boolean invalidArgument = false;

			do {
				invalidArgument = true;
				final JFrame f = new JFrame();
				final RegisterDialog dlg = new RegisterDialog(f,
					"Ultimate Tic-Tac-Toe - Registro", true);
				dlg.setLocationRelativeTo(null);
				dlg.setVisible(true);

				if (dlg.getSelection() == true) {
					System.out.println("Iniciando registro");
						stw.NoticeLabel.setText("Usuario registrado con éxito.");
						stw.NoticeLabel.setVisible(true);
						NoticeLabel.setForeground(new Color(0, 200, 0));
						NoticeLabel.setOpaque(true);
						NoticeLabel.setBorder(border);
						stw.EmailTextField.setText(dlg.getEmail());
						stw.PasswdField.requestFocusInWindow();
						invalidArgument = false;
					System.out.println("Fin registro");
				} else {
					stw.NoticeLabel.setText("");
					stw.NoticeLabel.setVisible(false);
					invalidArgument = false;
				}
				f.dispose();
			} while (invalidArgument);
		}
	}

	private class CreateSessionAction extends AbstractAction {

		private static final long serialVersionUID = 7000145813855380346L;
		private final StartupWindow win;

		public CreateSessionAction(StartupWindow win) {
			this.win = win;
		}

		@Override
		public void actionPerformed(ActionEvent event) {
			win.enviarDatosLogin(win.getEmailTextField().getText(), win.getPasswdField().getText());
		}

	}

	public void enviarDatosLogin(String email, String passwd) {
		Controller cntrl;
		cntrl = Controller.get();
		final PlayerListWindow playerListWindow = new PlayerListWindow(email);
		playerListWindow.setLocationRelativeTo(null);
		cntrl.enviarDatosLogin(email, passwd);
	}	
	
	@Override
	public void recibirRespuestaLogin(boolean error) {
		if (error) {
			NoticeLabel.setText("El Usuario introducido ya existe");
			NoticeLabel.setForeground(new Color(200, 0, 0));
			NoticeLabel.setOpaque(true);
			NoticeLabel.setBorder(border);
		} else {
			this.setVisible(false);
			//final PlayerListWindow playerListWindow = new PlayerListWindow();
			//playerListWindow.setLocationRelativeTo(null);
			//playerListWindow.setVisible(true);
		}		
	}

	@Override
	public void avisoCerrarSesion() {
		this.PasswdField.setText("");
		this.setVisible(true);
	}

	@Override
	public void jugadorNoExiste() {
		JOptionPane.showMessageDialog(this,"Jugador no existe", "Error de login",JOptionPane.ERROR_MESSAGE);

	}

	@Override
	public void jugadorYaExiste() {
		JOptionPane.showMessageDialog(this,"Jugador ya esta en el sistema", "Error de login",JOptionPane.ERROR_MESSAGE);
		
	}

	@Override
	public void excepcionRemota() {
		JOptionPane.showMessageDialog(this,"No se obtiene respuesta del servidor", "Error de red",JOptionPane.ERROR_MESSAGE);
		System.exit(1);
		
	}

	@Override
	public void mostrar() {
		this.setVisible(true);
	}

}
