package client.presentation;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import client.controller.Controller;

import java.awt.Color;
import java.util.Vector;

public class PlayerListWindow extends JFrame implements WindowListener, IListaJugadores {
	
	private static final long serialVersionUID = -5107198177153703399L;
	
	private JToolBar mGameListToolBar = null;
	private JPanel mGameListPanel = null;
	private JTable mPlayerList = null;
	private JTable mPlayList = null;
	
	private JToolBar mPlayToolBar = null;
	private JPanel mGamePanel = null;
	private JPanel mGameInfoPanel = null;
	
	private JButton logoutButton; //Botón para quitar y salir del juego (cerrar sesión)
	private JButton joinGameButton; //Botón para unirse a una partida
	private JButton updateListButton;
	private JButton createGameButton;
	
	public PlayerListWindow() {
		super();
		try {
			Controller cntrl;
			cntrl = Controller.get();
			cntrl.setLista(this);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setupListGUI();
	}
	

	private void setupListGUI() {		
		// TODO Auto-generated method stub
		this.setResizable(false);
		this.setTitle("Ultimate Tic-Tac-Toe");
		this.setSize(900, 650);
		this.addWindowListener(this);
		
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

			updateListButton = new JButton("Actualizar lista");
			updateListButton.setIcon(new ImageIcon(
				this.getClass().getClassLoader().getResource(
					"image/refresh.png")));
			updateListButton.addMouseListener(new UpdateListMouseAdapter());
			mGameListToolBar.add(updateListButton);

			createGameButton = new JButton("Crear partida");
			createGameButton.setIcon(new ImageIcon(
				this.getClass().getClassLoader().getResource(
					"image/ttoe.png")));
			createGameButton.addMouseListener(new CreateGameMouseAdapter(this));
			mGameListToolBar.add(createGameButton);

			logoutButton = new JButton("Cerrar sesión");
			logoutButton.setIcon(new ImageIcon(
				this.getClass().getClassLoader().getResource(
					"image/logout.png")));
			logoutButton.addMouseListener(new LogoutMouseAdapter(this));
			mGameListToolBar.add(logoutButton);

		}
		return mGameListToolBar;
	}


	private JPanel getGameListPanel() {
		if (mGameListPanel == null) {
			mGameListPanel = new JPanel();
			mGameListPanel.setBackground(Color.WHITE);
			mGameListPanel.setLayout(new BoxLayout(mGameListPanel,
				BoxLayout.Y_AXIS));
			mPlayList = new JTable();
			final JScrollPane playListPanel = new JScrollPane(mPlayList);
			
			mPlayerList = new JTable(new DefaultTableModel(new Object[]{"Email"}, 0));			
			mPlayerList.setBackground(Color.WHITE);
			final JScrollPane playerListPanel = new JScrollPane(mPlayerList);

			mPlayList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			mPlayerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

//			final GameListObserver listObserver = new GameListObserver(this);

//			mPlayerList.getSelectionModel().addListSelectionListener(listObserver);
//			mPlayList.getSelectionModel().addListSelectionListener(listObserver);

			mGameListPanel.add(new JLabel("Jugadores disponibles"));
			mGameListPanel.add(playerListPanel);
			mGameListPanel.add(new JLabel("Partidas Abiertas"));
			mGameListPanel.add(playListPanel);
		}
		return mGameListPanel;
	}
	
	//Miniclase que captura los cambios en las listas de partidas
//		private class GameListObserver implements ListSelectionListener {
//			private final PlayerListWindow win;
//
//			public GameListObserver(PlayerListWindow win) {
//				this.win = win;
//			}
//
//			@Override
//			public void valueChanged(ListSelectionEvent arg0) {
//				win.joinGameButton.setEnabled(mPlayerList.getSelectedRow() != -1);
//			}
//			
//		}

	

	
	private class UpdateListMouseAdapter extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent evt) {

		}
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
				try {
					Controller cntrl = Controller.get();
					cntrl.retarJugador(retado);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
			try {
				final int confirm = JOptionPane.showConfirmDialog(
					mGameListPanel,
					"¿Está seguro de que desea salir?",
					"Confirmación", JOptionPane.YES_NO_OPTION);
				if (confirm == 0) {
					win.dispose();
					Controller c = Controller.get();
					c.cerrarSesion();
				}
			} catch (final Exception e) {
				
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
	public void cerrarSesion() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void recibirRespuestaLista(Vector<String> jugadores) {
		if (!this.isVisible()) this.setVisible(true);
		DefaultTableModel model = (DefaultTableModel) this.mPlayerList.getModel();
		int rowCount = model.getRowCount();
		for(int i = rowCount - 1; i >=0; i--){
		    model.removeRow(i);
		}
		for (String jugador : jugadores) {
			model.addRow(new String[]{jugador});
		}
	}


	@Override
	public void recibirRespuestaReto(String retado, boolean respuesta, int id_partida) {
		if (respuesta) {
			iniciarPartida(id_partida);
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
	public void iniciarPartida(int id_partida) {
		final GameWindow gw = new GameWindow(id_partida);
		gw.setLocationRelativeTo(null);
		gw.setVisible(true);
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
			try {
				Controller cntl = Controller.get();
				if (confirm == 0) cntl.enviarRespuestaReto(true, retador);
				else cntl.enviarRespuestaReto(false, retador);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	  }
	} 
