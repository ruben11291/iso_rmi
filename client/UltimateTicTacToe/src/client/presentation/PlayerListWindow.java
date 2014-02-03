package client.presentation;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.html.HTMLDocument.Iterator;

import client.controller.Controller;

import java.awt.Color;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import javax.swing.SwingConstants;

public class PlayerListWindow extends JFrame implements WindowListener, IListaJugadores {
	
	private static final long serialVersionUID = -5107198177153703399L;
	
	private JToolBar mGameListToolBar = null;
	private JPanel mGameListPanel = null;
	private JLabel selfLabel = null;
	private JTable mPlayerList = null;
	private JTable mPlayingList = null;
	
	private JToolBar mPlayToolBar = null;
	private JPanel mGamePanel = null;
	private JPanel mGameInfoPanel = null;
	
	private JButton logoutButton;
	private JButton createGameButton;
	
	public PlayerListWindow(String self) {
		super();
		this.selfLabel = new JLabel("Sesión iniciada de: "+self);
		Controller cntrl;
		cntrl = Controller.get();
		cntrl.setLista(this);
		this.setupListGUI();
	}
	

	private void setupListGUI() {		
		this.setResizable(false);
		this.setTitle("Ultimate Tic-Tac-Toe");
		this.setSize(250, 400);
		this.addWindowListener(this);
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		
		if (mPlayToolBar != null) {
			mPlayToolBar.setVisible(false);
			mPlayToolBar = null;
		}
		if (mGamePanel != null) {
			mGamePanel.setVisible(false);
			mGamePanel = null;
		}
		if (mGameInfoPanel != null) {
			mGameInfoPanel.setVisible(false);
			mGameInfoPanel = null;
		}
;
		this.getContentPane().add(this.getGameListToolBar(), BorderLayout.NORTH);
		this.getContentPane().add(this.getGameListPanel(), BorderLayout.CENTER);
		mGameListToolBar.setVisible(true);
		this.getGameListPanel().setVisible(true);
	}

	private JToolBar getGameListToolBar() {
		// TODO Auto-generated method stub
		if (mGameListPanel == null) {
			mGameListToolBar = new JToolBar();
			
			createGameButton = new JButton("Retar");
			createGameButton.setIcon(new ImageIcon(
				this.getClass().getClassLoader().getResource(
					"image/ttoe.png")));
//			createGameButton.setBackground(new Color(143,188,143));
			createGameButton.setForeground(Color.BLACK);
			createGameButton.addMouseListener(new CreateGameMouseAdapter(this));
			
			mGameListToolBar.add(createGameButton);
			
			mGameListToolBar.addSeparator();
			
			logoutButton = new JButton("Cerrar sesión");
			logoutButton.setIcon(new ImageIcon(
				this.getClass().getClassLoader().getResource(
					"image/logout.png")));
//			logoutButton.setBackground(new Color(143,188,143));
			logoutButton.setForeground(Color.BLACK);
			logoutButton.addMouseListener(new LogoutMouseAdapter(this));
			mGameListToolBar.add(logoutButton);

		}
		return mGameListToolBar;
	}


	private JPanel getGameListPanel() {
		if (mGameListPanel == null) {
			mGameListPanel = new JPanel();
			mGameListPanel.setBackground(new Color(0,139,139));
			mGameListPanel.setLayout(new BoxLayout(mGameListPanel,
				BoxLayout.Y_AXIS));
			
			mPlayerList = new JTable(new DefaultTableModel(new Object[]{"Jugadores disponibles"}, 0){
				 @Override
				 public boolean isCellEditable (int fila, int columna) {
				        return false;
				 }
			});		
			
			mPlayingList = new JTable(new DefaultTableModel(new Object[]{"Jugando"}, 0){
				 @Override
				 public boolean isCellEditable (int fila, int columna) {
				        return false;
				 }
			});	
			
			DefaultTableCellRenderer r = new DefaultTableCellRenderer() {
	            @Override
	            public Component getTableCellRendererComponent(JTable table, Object
	                value, boolean isSelected, boolean hasFocus, int row, int column) {
	                super.getTableCellRendererComponent(
	                    table, value, isSelected, hasFocus, row, column);
	                setForeground(Color.blue);
	                setHorizontalAlignment(JLabel.CENTER);
	                return this;
	            }
	        };
	        
			selfLabel.setBackground(new Color(0,139,139));
			selfLabel.setFont(new Font("Dialog", Font.BOLD, 14));
			selfLabel.setForeground(Color.WHITE);
			selfLabel.setOpaque(true);
			selfLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			selfLabel.setHorizontalAlignment(SwingConstants.CENTER);
	        mGameListPanel.add(this.selfLabel);
	        
			mPlayerList.setBackground(Color.WHITE);
			mPlayerList.setFont(new Font("Dialog", Font.BOLD, 16));
			mPlayerList.setForeground(Color.GREEN);
			mPlayerList.getColumnModel().getColumn(0).setCellRenderer(r);
			mPlayerList.getTableHeader().setFont(new Font( "FreeSans" , Font.BOLD, 17 ));
			
			mPlayerList.setRowHeight(25);
			final JScrollPane playerListPanel = new JScrollPane(mPlayerList);

			mPlayerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			mGameListPanel.add(playerListPanel);
			
			mPlayingList.setBackground(Color.WHITE);
			mPlayingList.setFont(new Font("Dialog", Font.BOLD, 16));
			mPlayingList.setForeground(Color.BLUE);
			mPlayingList.getColumnModel().getColumn(0).setCellRenderer(r);
			mPlayingList.getTableHeader().setFont(new Font( "FreeSans" , Font.BOLD, 17 ));
			
			mPlayingList.setRowHeight(25);
			final JScrollPane playingListPanel = new JScrollPane(mPlayingList);

			mPlayingList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			mGameListPanel.add(playingListPanel);
			
		}
		return mGameListPanel;
	}
			
	private class CreateGameMouseAdapter extends MouseAdapter {
		PlayerListWindow win;
		public CreateGameMouseAdapter(PlayerListWindow win) {
			this.win = win;
		}
		
		@Override
		public void mouseClicked(MouseEvent evt) {
			if (this.win.mPlayerList.getSelectedRow() != -1 || this.win.mPlayerList.getSelectedRow() >= this.win.mPlayerList.getRowCount()) {
				String retado = this.win.mPlayerList.getModel().getValueAt(this.win.mPlayerList.getSelectedRow(), this.win.mPlayerList.getSelectedColumn()).toString();
				Controller cntrl = Controller.get();
				cntrl.retarJugador(retado);
				
			}
		}
	}
	
	private class LogoutMouseAdapter extends MouseAdapter {
		PlayerListWindow win;

		public LogoutMouseAdapter(PlayerListWindow win) {
			this.win = win;
		}

		@Override
		public void mouseClicked(MouseEvent evt) {
			System.out.println("Haciendo logout...");
			//Aquí todo lo de guardar los datos, las partidas y demás
			
			final int confirm = JOptionPane.showConfirmDialog(
				mGameListPanel,
				"¿Está seguro de que desea salir?",
				"Confirmación", JOptionPane.YES_NO_OPTION);
			if (confirm == 0) {
				win.dispose();
				Controller c = Controller.get();
				c.avisoCerrarSesion();
				c.cerrarSesion();
			}
		}
	}

	@Override
	public void windowActivated(WindowEvent e) {

		
	}


	@Override
	public void windowClosed(WindowEvent e) {
		
	}


	@Override
	public void windowClosing(WindowEvent e) {
		System.out.println("Haciendo logout...");
		//Aquí todo lo de guardar los datos, las partidas y demás
		
		final int confirm = JOptionPane.showConfirmDialog(
			mGameListPanel,
			"¿Está seguro de que desea salir?",
			"Confirmación", JOptionPane.YES_NO_OPTION);
		if (confirm == 0) {
			this.dispose();
			Controller c = Controller.get();
			c.avisoCerrarSesion();
			c.cerrarSesion();
		}
	}


	@Override
	public void windowDeactivated(WindowEvent e) {
		
	}


	@Override
	public void windowDeiconified(WindowEvent e) {

		
	}


	@Override
	public void windowIconified(WindowEvent e) {
		
	}


	@Override
	public void windowOpened(WindowEvent e) {
		
	}

	

	@Override
	public void recibirRespuestaLista(Hashtable <String, Integer> jugadores) {
		if (!this.isVisible()) this.setVisible(true);
		DefaultTableModel modelDisponibles = (DefaultTableModel) this.mPlayerList.getModel();
		DefaultTableModel modelJugando = (DefaultTableModel) this.mPlayingList.getModel();
		int rowCount = modelDisponibles.getRowCount();
		for(int i = rowCount - 1; i >=0; i--){
		    modelDisponibles.removeRow(i);
		}
		rowCount = modelJugando.getRowCount();
		for(int i = rowCount - 1; i >=0; i--){
		    modelJugando.removeRow(i);
		}
		Enumeration<String> emails = jugadores.keys();
		while (emails.hasMoreElements()) {
			String email = emails.nextElement();
			if (!email.equals(this.selfLabel.getText()))
				if (jugadores.get(email) == 0)
					modelDisponibles.addRow(new String[]{email});
				else
					modelJugando.addRow(new String[]{email});
		}
	}


	@Override
	public void recibirRespuestaReto(String self, String retador, String retado, boolean respuesta) {
		if (respuesta) {
			iniciarPartida(self, retador, retado);
		} else {
			System.out.println(retado + " ha rechazado el reto.");
			JOptionPane.showMessageDialog(null, retado + " ha rechazado el reto.", "Respuesta reto", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	@Override
	public void mostrarMensaje(String mensaje) {
		JOptionPane.showMessageDialog(this.mGameListPanel, mensaje, "Respuesta reto", JOptionPane.ERROR_MESSAGE);
	}
	
	@Override
	public void recibirReto(String retador) {
		ConfirmDialogThread thread = new ConfirmDialogThread(retador);
		thread.start();
	}
	
	@Override
	public void iniciarPartida(String self, String retador, String retado) {
		final GameWindow gw = new GameWindow(self, retador, retado); // TODO
		gw.setLocationRelativeTo(null);
		gw.setVisible(true);
	}


	@Override
	public void excepcionRemota() {
		JOptionPane.showMessageDialog(this,"No se obtiene respuesta del servidor", "Error de red",JOptionPane.ERROR_MESSAGE);
		this.dispose();
		
	}	
	
}

class ConfirmDialogThread extends Thread {
	  private final String retador;

	  ConfirmDialogThread(String retador) {
	    this.retador = retador;
	  }

	  @Override
	  public void run() {
			final int confirm = JOptionPane.showConfirmDialog(null, retador + " te ha retado, ¿aceptas?", "¡Te han retado!", JOptionPane.YES_NO_OPTION);
			System.out.println("Respuesta: " + confirm);
			Controller cntl = Controller.get();
			if (confirm == 0) cntl.enviarRespuestaReto(true, retador);
			else cntl.enviarRespuestaReto(false, retador);
	  }
	} 
