package client.presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Hashtable;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import client.controller.Controller;
import client.exceptions.MovimientoNoValidoException;
import client.exceptions.NoTienesElTurnoException;

import java.awt.Font;

public class GameWindow extends JFrame implements IJuego {

	private static final long serialVersionUID = -5128501222928885944L;

	private JPanel StartupPanel;
	private JLabel mapLabel;

	private JLabel F1, F2, F3, F4, F5, F6, F7, F8, F9; //TABLERO GLOBAL
	
	
	private JLabel [][] tableroGlobal =  new JLabel[3][3];
	
	private class Coordenada{
		private int x;
		private int y;
		public Coordenada(int i, int j) {
			this.x = i;
			this.y = j;
		}
		public int getx(){return this.x;}
		public int gety(){return this.y;}
	
		@Override
		public String toString(){return x+" "+y;}
	}
	
	 private JLabel [][] tableroJuego = new JLabel[9][9];
	 private Hashtable <JLabel, Coordenada> coordenadas = new Hashtable<JLabel, Coordenada> ();
	
	private JLabel minimapLabel;
	private JLabel ply1;
	private JLabel ply2;
	private JLabel notification;
	private JLabel ply2name;
	private JLabel ply1name;
	private JLabel player1;
	private JLabel player2;
	private JLabel globalview;
	private JLabel ultimate, tictactoe;
	private JLabel turnoPly1;
	private JLabel turnoPly2;

	
	public GameWindow(String self, String retador, String retado) {
		super();
		
		try {
			Controller cntrl;
			cntrl = Controller.get();
			cntrl.setJuego(this);
		} catch (Exception e) {
			e.printStackTrace();
		}	
		this.initGUI(self, retador, retado);
	}

	private void initGUI(String self, String retador, String retado) {
		
		this.setResizable(false);
		this.setTitle("Ultimate Tic-Tac-Toe - GAME  Player " + self + ". " + retador + " vs " + retado);
		this.setSize(980, 730);

		StartupPanel = new JPanel();
		StartupPanel.setBackground(UIManager.getColor("Button.select"));
		this.getContentPane().add(StartupPanel, BorderLayout.CENTER);
		StartupPanel.setLayout(null);
	
		
		/*----------------------------- Casillas TABLERO 1 -----------------------------------------*/
		tableroJuego[0][0] = new JLabel();
		tableroJuego[0][0].setBackground(Color.WHITE);
		tableroJuego[0][0].setBounds(49, 126, 48, 53);
		tableroJuego[0][0].addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                FichaMouseClicked(evt, tableroJuego[0][0]);
            }
        });
		this.coordenadas.put(tableroJuego[0][0], new Coordenada(0,0));
		StartupPanel.add(tableroJuego[0][0]);
		
		tableroJuego[0][1] = new JLabel();
		tableroJuego[0][1].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, tableroJuego[0][1]);
			}
		});
		tableroJuego[0][1].setBackground(UIManager.getColor("Button.WITHE"));
		tableroJuego[0][1].setBounds(109, 126, 53, 53);
		StartupPanel.add(tableroJuego[0][1]);
		this.coordenadas.put(tableroJuego[0][1], new Coordenada(0,1));
		
		tableroJuego[0][2] = new JLabel();
		tableroJuego[0][2].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, tableroJuego[0][2]);
			}
		});
		tableroJuego[0][2].setBackground(UIManager.getColor("Button.WHITE"));
		tableroJuego[0][2].setBounds(175, 126, 48, 53);
		StartupPanel.add(tableroJuego[0][2]);
		this.coordenadas.put(tableroJuego[0][2], new Coordenada(0,2));
		
		tableroJuego[0][3] = new JLabel();
		tableroJuego[0][3].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, tableroJuego[0][3]);
			}
		});
		this.coordenadas.put(tableroJuego[0][3], new Coordenada(0,3));
		
		tableroJuego[0][3].setBackground(UIManager.getColor("Button.WHITE"));
		tableroJuego[0][3].setBounds(49, 191, 48, 53);
		StartupPanel.add(tableroJuego[0][3]);
		
		tableroJuego[0][4] = new JLabel();
		tableroJuego[0][4].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, tableroJuego[0][4]);
			}
		});
		

		tableroJuego[0][4].setBackground(UIManager.getColor("Button.WHITE"));
		tableroJuego[0][4].setBounds(113, 195, 53, 49);
		StartupPanel.add(tableroJuego[0][4]);
		this.coordenadas.put(tableroJuego[0][4], new Coordenada(0,4));
		
		tableroJuego[0][5]= new JLabel();
		tableroJuego[0][5].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, tableroJuego[0][5]);
			}
		});
		tableroJuego[0][5].setBackground(UIManager.getColor("Button.WHITE"));
		tableroJuego[0][5].setBounds(175, 191, 48, 53);
		StartupPanel.add(tableroJuego[0][5]);
		this.coordenadas.put(tableroJuego[0][5], new Coordenada(0,5));
		
		tableroJuego[0][6] = new JLabel();
		tableroJuego[0][6].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, tableroJuego[0][6]);
			}
		});
		tableroJuego[0][6].setBounds(49, 253, 55, 53);
		StartupPanel.add(tableroJuego[0][6]);
		this.coordenadas.put(tableroJuego[0][6], new Coordenada(0,6));
		
		tableroJuego[0][7] = new JLabel();
		tableroJuego[0][7].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, tableroJuego[0][7]);
			}
		});
		tableroJuego[0][7].setBounds(109, 256, 55, 50);
		StartupPanel.add(tableroJuego[0][7]);
		this.coordenadas.put(tableroJuego[0][7], new Coordenada(0,7));
		
		tableroJuego[0][8] = new JLabel();
		tableroJuego[0][8].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, tableroJuego[0][8]);
			}
		});
		tableroJuego[0][8].setBounds(175, 256, 48, 50);
		StartupPanel.add(tableroJuego[0][8]);
		this.coordenadas.put(tableroJuego[0][8], new Coordenada(0,8));
		/*-------------------------------------------------------------------------------------*/
		
		/*-------------------------------Casillas TABLERO 2 -------------------------------------------*/
		tableroJuego[1][0] = new JLabel();
		tableroJuego[1][0].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, tableroJuego[1][0]);
			}
		});
		tableroJuego[1][0].setBounds(235, 131, 53, 48);
		StartupPanel.add(tableroJuego[1][0]);
		this.coordenadas.put(tableroJuego[1][0], new Coordenada(1,0));
		
		tableroJuego[1][1] = new JLabel();
		tableroJuego[1][1].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, tableroJuego[1][1]);
			}
		});
		tableroJuego[1][1].setBounds(295, 131, 53, 48);
		StartupPanel.add(tableroJuego[1][1]);
		this.coordenadas.put(tableroJuego[1][1], new Coordenada(1,1));

		tableroJuego[1][2] = new JLabel();
		tableroJuego[1][2].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, tableroJuego[1][2]);
			}
		});
		tableroJuego[1][2].setBounds(362, 131, 48, 48);
		StartupPanel.add(tableroJuego[1][2]);
		this.coordenadas.put(tableroJuego[1][2], new Coordenada(1,2));

		tableroJuego[1][3] = new JLabel();
		tableroJuego[1][3].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, tableroJuego[1][3]);
			}
		});
		tableroJuego[1][3].setBounds(235, 191, 53, 53);
		StartupPanel.add(tableroJuego[1][3]);
		this.coordenadas.put(tableroJuego[1][3], new Coordenada(1,3));

		tableroJuego[1][4] = new JLabel();
		tableroJuego[1][4].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, tableroJuego[1][4]);
			}
		});
		tableroJuego[1][4].setBounds(296, 191, 55, 53);
		StartupPanel.add(tableroJuego[1][4]);
		this.coordenadas.put(tableroJuego[1][4], new Coordenada(1,4));

		tableroJuego[1][5] = new JLabel();
		tableroJuego[1][5].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, tableroJuego[1][5]);
			}
		});
		tableroJuego[1][5].setBounds(362, 191, 48, 53);
		StartupPanel.add(tableroJuego[1][5]);
		this.coordenadas.put(tableroJuego[1][5], new Coordenada(1,5));

		tableroJuego[1][6] = new JLabel();
		tableroJuego[1][6].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, tableroJuego[1][6]);
			}
		});
		tableroJuego[1][6].setBounds(235, 256, 53, 49);
		StartupPanel.add(tableroJuego[1][6]);
		this.coordenadas.put(tableroJuego[1][6], new Coordenada(1,6));

		tableroJuego[1][7] = new JLabel();
		tableroJuego[1][7].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, tableroJuego[1][7]);
			}
		});
		tableroJuego[1][7].setBounds(295, 252, 53, 53);
		StartupPanel.add(tableroJuego[1][7]);
		this.coordenadas.put(tableroJuego[1][7], new Coordenada(1,7));

		tableroJuego[1][8] = new JLabel();
		tableroJuego[1][8].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, tableroJuego[1][8]);
			}
		});
		tableroJuego[1][8].setBounds(365, 256, 47, 49);
		StartupPanel.add(tableroJuego[1][8]);
		this.coordenadas.put(tableroJuego[1][8], new Coordenada(1,8));

		/*-------------------------------------------------------------------------------------*/
		
		/*--------------------------------Casillas TABLERO 3-----------------------------------*/
		
		tableroJuego[2][0] = new JLabel();
		tableroJuego[2][0].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, tableroJuego[2][0]);
			}
		});
		tableroJuego[2][0].setBounds(423, 131, 48, 48);
		StartupPanel.add(tableroJuego[2][0]);
		this.coordenadas.put(tableroJuego[2][0], new Coordenada(2,0));

		tableroJuego[2][1] = new JLabel();
		tableroJuego[2][1].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, tableroJuego[2][1]);
			}
		});
		tableroJuego[2][1].setBounds(478, 133, 53, 46);
		StartupPanel.add(tableroJuego[2][1]);
		this.coordenadas.put(tableroJuego[2][1], new Coordenada(2,1));

		tableroJuego[2][2] = new JLabel();
		tableroJuego[2][2].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, tableroJuego[2][2]);
			}
		});
		tableroJuego[2][2].setBounds(545, 131, 48, 48);
		StartupPanel.add(tableroJuego[2][2]);
		this.coordenadas.put(tableroJuego[2][2], new Coordenada(2,2));

		tableroJuego[2][3] = new JLabel();
		tableroJuego[2][3].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, tableroJuego[2][3]);
			}
		});
		tableroJuego[2][3].setBounds(422, 191, 49, 49);
		StartupPanel.add(tableroJuego[2][3]);
		this.coordenadas.put(tableroJuego[2][3], new Coordenada(2,3));

		
		tableroJuego[2][4] = new JLabel();
		tableroJuego[2][4].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				FichaMouseClicked(e, tableroJuego[2][4]);
			}
		});
		tableroJuego[2][4].setBounds(484, 194, 48, 50);
		StartupPanel.add(tableroJuego[2][4]);
		this.coordenadas.put(tableroJuego[2][4], new Coordenada(2,4));

		tableroJuego[2][5] = new JLabel();
		tableroJuego[2][5].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, tableroJuego[2][5]);
			}
		});
		tableroJuego[2][5].setBounds(545, 191, 48, 53);
		StartupPanel.add(tableroJuego[2][5]);
		this.coordenadas.put(tableroJuego[2][5], new Coordenada(2,5));

		tableroJuego[2][6] = new JLabel();
		tableroJuego[2][6].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, tableroJuego[2][6]);
			}
		});
		tableroJuego[2][6].setBounds(424, 253, 42, 53);
		StartupPanel.add(tableroJuego[2][6]);
		this.coordenadas.put(tableroJuego[2][6], new Coordenada(2,6));

		tableroJuego[2][7] = new JLabel();
		tableroJuego[2][7].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, tableroJuego[2][7]);
			}
		});
		tableroJuego[2][7].setBounds(482, 259, 55, 47);
		StartupPanel.add(tableroJuego[2][7]);
		this.coordenadas.put(tableroJuego[2][7], new Coordenada(2,7));

		tableroJuego[2][8] = new JLabel();
		tableroJuego[2][8].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, tableroJuego[2][8]);
			}
		});
		tableroJuego[2][8].setBounds(550, 256, 48, 50);
		StartupPanel.add(tableroJuego[2][8]);
		this.coordenadas.put(tableroJuego[2][8], new Coordenada(2,8));

		/*-------------------------------------------------------------------------------------*/
		
		/*--------------------------------Casillas TABLERO 4-----------------------------------*/
		tableroJuego[3][0] = new JLabel();
		tableroJuego[3][0].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, tableroJuego[3][0]);
			}
		});
		tableroJuego[3][0].setBounds(49, 317, 44, 49);
		StartupPanel.add(tableroJuego[3][0]);
		this.coordenadas.put(tableroJuego[3][0], new Coordenada(3,0));

		tableroJuego[3][1] = new JLabel();
		tableroJuego[3][1].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, tableroJuego[3][1]);
			}
		});
		tableroJuego[3][1].setBounds(109, 312, 53, 54);
		StartupPanel.add(tableroJuego[3][1]);
		this.coordenadas.put(tableroJuego[3][1], new Coordenada(3,1));

		tableroJuego[3][2] = new JLabel();
		tableroJuego[3][2].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, tableroJuego[3][2]);
			}
		});
		tableroJuego[3][2].setBounds(175, 318, 48, 49);
		StartupPanel.add(tableroJuego[3][2]);
		this.coordenadas.put(tableroJuego[3][2], new Coordenada(3,2));

		tableroJuego[3][3] = new JLabel();
		tableroJuego[3][3].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, tableroJuego[3][3]);
			}
		});
		tableroJuego[3][3].setBounds(49, 379, 48, 49);
		StartupPanel.add(tableroJuego[3][3]);
		this.coordenadas.put(tableroJuego[3][2], new Coordenada(3,2));

		tableroJuego[3][4] = new JLabel();
		tableroJuego[3][4].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, tableroJuego[3][4]);
			}
		});
		tableroJuego[3][4].setBounds(109, 379, 53, 49);
		StartupPanel.add(tableroJuego[3][4]);
		this.coordenadas.put(tableroJuego[3][4], new Coordenada(3,4));

		tableroJuego[3][5] = new JLabel();
		tableroJuego[3][5].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, tableroJuego[3][5]);
			}
		});
		tableroJuego[3][5].setBounds(175, 379, 48, 49);
		StartupPanel.add(tableroJuego[3][5]);
		this.coordenadas.put(tableroJuego[3][5], new Coordenada(3,5));

		tableroJuego[3][6] = new JLabel();
		tableroJuego[3][6].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, tableroJuego[3][6]);
			}
		});
		tableroJuego[3][6].setBounds(49, 439, 48, 50);
		StartupPanel.add(tableroJuego[3][6]);
		this.coordenadas.put(tableroJuego[3][6], new Coordenada(3,6));

		tableroJuego[3][7] = new JLabel();
		tableroJuego[3][7].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, tableroJuego[3][7]);
			}
		});
		tableroJuego[3][7].setBounds(109, 440, 48, 49);
		StartupPanel.add(tableroJuego[3][7]);
		this.coordenadas.put(tableroJuego[3][7], new Coordenada(3,7));

		tableroJuego[3][8] = new JLabel();
		tableroJuego[3][8].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, tableroJuego[3][8]);
			}
		});
		tableroJuego[3][8].setBounds(175, 440, 48, 49);
		StartupPanel.add(tableroJuego[3][8]);
		this.coordenadas.put(tableroJuego[3][8], new Coordenada(3,8));

		/*----------------------------------------------------------------------------------*/
		
		/*--------------------------------Casillas TABLERO 5-----------------------------------*/
		tableroJuego[4][0] = new JLabel();
		tableroJuego[4][0].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, tableroJuego[4][0]);
			}
		});
		tableroJuego[4][0].setBounds(235, 317, 44, 49);
		StartupPanel.add(tableroJuego[4][0]);
		this.coordenadas.put(tableroJuego[4][0], new Coordenada(4,0));

		tableroJuego[4][1] = new JLabel();
		tableroJuego[4][1].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, tableroJuego[4][1]);
			}
		});
		tableroJuego[4][1].setBounds(295, 317, 55, 50);
		StartupPanel.add(tableroJuego[4][1]);
		this.coordenadas.put(tableroJuego[4][1], new Coordenada(4,1));

		tableroJuego[4][2] = new JLabel();
		tableroJuego[4][2].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, tableroJuego[4][2]);
			}
		});
		tableroJuego[4][2].setBounds(362, 318, 48, 49);
		StartupPanel.add(tableroJuego[4][2]);
		this.coordenadas.put(tableroJuego[4][2], new Coordenada(4,2));

		tableroJuego[4][3] = new JLabel();
		tableroJuego[4][3].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, tableroJuego[4][3]);
			}
		});
		tableroJuego[4][3].setBounds(235, 379, 48, 49);
		StartupPanel.add(tableroJuego[4][3]);
		this.coordenadas.put(tableroJuego[4][3], new Coordenada(4,3));

		tableroJuego[4][4] = new JLabel();
		tableroJuego[4][4].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, tableroJuego[4][4]);
			}
		});
		tableroJuego[4][4].setBounds(295, 379, 53, 49);
		StartupPanel.add(tableroJuego[4][4]);
		this.coordenadas.put(tableroJuego[4][4], new Coordenada(4,4));

		tableroJuego[4][5] = new JLabel();
		tableroJuego[4][5].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, tableroJuego[4][5]);
			}
		});
		tableroJuego[4][5].setBounds(362, 379, 48, 49);
		StartupPanel.add(tableroJuego[4][5]);
		this.coordenadas.put(tableroJuego[4][5], new Coordenada(4,5));

		tableroJuego[4][6] = new JLabel();
		tableroJuego[4][6].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, tableroJuego[4][6]);
			}
		});
		tableroJuego[4][6].setBounds(235, 439, 48, 50);
		StartupPanel.add(tableroJuego[4][6]);
		this.coordenadas.put(tableroJuego[4][6], new Coordenada(4,6));

		tableroJuego[4][7] = new JLabel();
		tableroJuego[4][7].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, tableroJuego[4][7]);
			}
		});
		tableroJuego[4][7].setBounds(300, 440, 48, 49);
		StartupPanel.add(tableroJuego[4][7]);
		this.coordenadas.put(tableroJuego[4][7], new Coordenada(4,7));

		tableroJuego[4][8] = new JLabel();
		tableroJuego[4][8].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, tableroJuego[4][8]);
			}
		});
		tableroJuego[4][8].setBounds(362, 440, 48, 49);
		StartupPanel.add(tableroJuego[4][8]);
		this.coordenadas.put(tableroJuego[4][8], new Coordenada(4,8));

		/*-------------------------------------------------------------------------------------*/
		
		/*--------------------------------Casillas TABLERO 6-----------------------------------*/
		tableroJuego[5][0] = new JLabel();
		tableroJuego[5][0].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, tableroJuego[5][0]);
			}
		});
		tableroJuego[5][0].setBounds(423, 317, 44, 49);
		StartupPanel.add(tableroJuego[5][0]);
		this.coordenadas.put(tableroJuego[5][0], new Coordenada(5,0));

		tableroJuego[5][1] = new JLabel();
		tableroJuego[5][1].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, tableroJuego[5][1]);
			}
		});
		tableroJuego[5][1].setBounds(484, 318, 55, 50);
		StartupPanel.add(tableroJuego[5][1]);
		this.coordenadas.put(tableroJuego[5][1], new Coordenada(5,1));

		tableroJuego[5][2] = new JLabel();
		tableroJuego[5][2].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, tableroJuego[5][2]);
			}
		});
		tableroJuego[5][2].setBounds(545, 317, 48, 49);
		StartupPanel.add(tableroJuego[5][2]);
		this.coordenadas.put(tableroJuego[5][2], new Coordenada(5,2));

		tableroJuego[5][3] = new JLabel();
		tableroJuego[5][3].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, tableroJuego[5][3]);
			}
		});
		tableroJuego[5][3].setBounds(423, 379, 48, 49);
		StartupPanel.add(tableroJuego[5][3]);
		this.coordenadas.put(tableroJuego[5][3], new Coordenada(5,3));

		tableroJuego[5][4] = new JLabel();
		tableroJuego[5][4].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, tableroJuego[5][4]);
			}
		});
		tableroJuego[5][4].setBounds(484, 379, 53, 49);
		StartupPanel.add(tableroJuego[5][4]);
		this.coordenadas.put(tableroJuego[5][4], new Coordenada(5,4));

		tableroJuego[5][5] = new JLabel();
		tableroJuego[5][5].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, tableroJuego[5][5]);
			}
		});
		tableroJuego[5][5].setBounds(550, 379, 48, 49);
		StartupPanel.add(tableroJuego[5][5]);
		this.coordenadas.put(tableroJuego[5][5], new Coordenada(5,5));

		tableroJuego[5][6] = new JLabel();
		tableroJuego[5][6].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, tableroJuego[5][6]);
			}
		});
		tableroJuego[5][6].setBounds(423, 439, 48, 50);
		StartupPanel.add(tableroJuego[5][6]);
		this.coordenadas.put(tableroJuego[5][6], new Coordenada(5,6));

		tableroJuego[5][7] = new JLabel();
		tableroJuego[5][7].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, tableroJuego[5][7]);
			}
		});
		tableroJuego[5][7].setBounds(484, 439, 48, 49);
		StartupPanel.add(tableroJuego[5][7]);
		this.coordenadas.put(tableroJuego[5][7], new Coordenada(5,7));

		tableroJuego[5][8] = new JLabel();
		tableroJuego[5][8].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, tableroJuego[5][8]);
			}
		});
		tableroJuego[5][8].setBounds(550, 440, 48, 49);
		StartupPanel.add(tableroJuego[5][8]);
		
		this.coordenadas.put(tableroJuego[5][8], new Coordenada(5,8));
/*---------------------------------------------------------*/
		
		/*--------------------------------Casillas TABLERO 7-----------------------------------*/
		tableroJuego[6][0] = new JLabel();
		tableroJuego[6][0].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, tableroJuego[6][0]);
			}
		});
		tableroJuego[6][0].setBounds(49, 501, 44, 49);
		StartupPanel.add(tableroJuego[6][0]);
		this.coordenadas.put(tableroJuego[6][0], new Coordenada(6,0));

		tableroJuego[6][1] = new JLabel();
		tableroJuego[6][1].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, tableroJuego[6][1]);
			}
		});
		tableroJuego[6][1].setBounds(105, 500, 55, 50);
		StartupPanel.add(tableroJuego[6][1]);
		this.coordenadas.put(tableroJuego[6][1], new Coordenada(6,1));

		tableroJuego[6][2] = new JLabel();
		tableroJuego[6][2].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, tableroJuego[6][2]);
			}
		});
		tableroJuego[6][2].setBounds(172, 501, 48, 49);
		StartupPanel.add(tableroJuego[6][2]);
		this.coordenadas.put(tableroJuego[6][2], new Coordenada(6,2));

		tableroJuego[6][3] = new JLabel();
		tableroJuego[6][3].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, tableroJuego[6][3]);
			}
		});
		tableroJuego[6][3].setBounds(49, 562, 48, 49);
		StartupPanel.add(tableroJuego[6][3]);
		this.coordenadas.put(tableroJuego[6][3], new Coordenada(6,3));

		tableroJuego[6][4] = new JLabel();
		tableroJuego[6][4].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, tableroJuego[6][4]);
			}
		});
		tableroJuego[6][4].setBounds(105, 562, 53, 49);
		StartupPanel.add(tableroJuego[6][4]);
		this.coordenadas.put(tableroJuego[6][4], new Coordenada(6,4));

		tableroJuego[6][5] = new JLabel();
		tableroJuego[6][5].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, tableroJuego[6][5]);
			}
		});
		tableroJuego[6][5].setBounds(172, 562, 48, 49);
		StartupPanel.add(tableroJuego[6][5]);
		this.coordenadas.put(tableroJuego[6][5], new Coordenada(6,5));

		tableroJuego[6][6] = new JLabel();
		tableroJuego[6][6].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, tableroJuego[6][6]);
			}
		});
		tableroJuego[6][6].setBounds(49, 623, 48, 50);
		StartupPanel.add(tableroJuego[6][6]);
		this.coordenadas.put(tableroJuego[6][6], new Coordenada(6,6));

		tableroJuego[6][7] = new JLabel();
		tableroJuego[6][7].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, tableroJuego[6][7]);
			}
		});
		tableroJuego[6][7].setBounds(109, 624, 48, 49);
		StartupPanel.add(tableroJuego[6][7]);
		this.coordenadas.put(tableroJuego[6][7], new Coordenada(6,7));

		tableroJuego[6][8] = new JLabel();
		tableroJuego[6][8].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, tableroJuego[6][8]);
			}
		});
		tableroJuego[6][8].setBounds(175, 624, 48, 49);
		StartupPanel.add(tableroJuego[6][8]);
		this.coordenadas.put(tableroJuego[6][8], new Coordenada(6,8));

		/*-------------------------------------------------------------------------------------*/
		
		/*--------------------------------Casillas TABLERO 8-----------------------------------*/
		tableroJuego[7][0] = new JLabel();
		tableroJuego[7][0].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, tableroJuego[7][0]);
			}
		});
		tableroJuego[7][0].setBounds(235, 501, 44, 49);
		StartupPanel.add(tableroJuego[7][0]);
		this.coordenadas.put(tableroJuego[7][0], new Coordenada(7,0));

		tableroJuego[7][1] = new JLabel();
		tableroJuego[7][1].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, tableroJuego[7][1]);
			}
		});
		tableroJuego[7][1].setBounds(293, 501, 55, 50);
		StartupPanel.add(tableroJuego[7][1]);
		this.coordenadas.put(tableroJuego[7][1], new Coordenada(7,1));

		tableroJuego[7][2] = new JLabel();
		tableroJuego[7][2].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, tableroJuego[7][2]);
			}
		});
		tableroJuego[7][2].setBounds(362, 501, 48, 49);
		StartupPanel.add(tableroJuego[7][2]);
		this.coordenadas.put(tableroJuego[7][2], new Coordenada(7,2));

		tableroJuego[7][3] = new JLabel();
		tableroJuego[7][3].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, tableroJuego[7][3]);
			}
		});
		tableroJuego[7][3].setBounds(240, 562, 48, 49);
		StartupPanel.add(tableroJuego[7][3]);
		this.coordenadas.put(tableroJuego[7][3], new Coordenada(7,3));

		tableroJuego[7][4] = new JLabel();
		tableroJuego[7][4].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, tableroJuego[7][4]);
			}
		});
		tableroJuego[7][4].setBounds(295, 562, 53, 49);
		StartupPanel.add(tableroJuego[7][4]);
		this.coordenadas.put(tableroJuego[7][4], new Coordenada(7,4));

		tableroJuego[7][5] = new JLabel();
		tableroJuego[7][5].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, tableroJuego[7][5]);
			}
		});
		tableroJuego[7][5].setBounds(362, 562, 48, 49);
		StartupPanel.add(tableroJuego[7][5]);
		this.coordenadas.put(tableroJuego[7][5], new Coordenada(7,5));

		tableroJuego[7][6] = new JLabel();
		tableroJuego[7][6].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, tableroJuego[7][6]);
			}
		});
		tableroJuego[7][6].setBounds(235, 623, 48, 50);
		StartupPanel.add(tableroJuego[7][6]);
		this.coordenadas.put(tableroJuego[7][6], new Coordenada(7,6));

		tableroJuego[7][7] = new JLabel();
		tableroJuego[7][7].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, tableroJuego[7][7]);
			}
		});
		tableroJuego[7][7].setBounds(300, 623, 48, 49);
		StartupPanel.add(tableroJuego[7][7]);
		this.coordenadas.put(tableroJuego[7][7], new Coordenada(7,7));

		tableroJuego[7][8] = new JLabel();
		tableroJuego[7][8].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, tableroJuego[7][8]);
			}
		});
		tableroJuego[7][8].setBounds(362, 624, 48, 49);
		StartupPanel.add(tableroJuego[7][8]);
		this.coordenadas.put(tableroJuego[7][8], new Coordenada(7,8));

		/*-------------------------------------------------------------------------------------*/
		
		/*--------------------------------Casillas TABLERO 9-----------------------------------*/
		tableroJuego[8][0] = new JLabel();
		tableroJuego[8][0].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, tableroJuego[8][0]);
			}
		});
		tableroJuego[8][0].setBounds(423, 501, 44, 49);
		StartupPanel.add(tableroJuego[8][0]);
		this.coordenadas.put(tableroJuego[8][0], new Coordenada(8,0));

		tableroJuego[8][1] = new JLabel();
		tableroJuego[8][1].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, tableroJuego[8][1]);
			}
		});
		tableroJuego[8][1].setBounds(482, 500, 55, 50);
		StartupPanel.add(tableroJuego[8][1]);
		this.coordenadas.put(tableroJuego[8][1], new Coordenada(8,1));

		tableroJuego[8][2] = new JLabel();
		tableroJuego[8][2].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, tableroJuego[8][2]);
			}
		});
		tableroJuego[8][2].setBounds(545, 501, 48, 49);
		StartupPanel.add(tableroJuego[8][2]);
		this.coordenadas.put(tableroJuego[8][2], new Coordenada(8,2));

		tableroJuego[8][3] = new JLabel();
		tableroJuego[8][3].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, tableroJuego[8][3]);
			}
		});
		tableroJuego[8][3].setBounds(423, 562, 48, 49);
		StartupPanel.add(tableroJuego[8][3]);
		this.coordenadas.put(tableroJuego[8][3], new Coordenada(8,3));

		tableroJuego[8][4] = new JLabel();
		tableroJuego[8][4].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, tableroJuego[8][4]);
			}
		});
		tableroJuego[8][4].setBounds(484, 562, 53, 49);
		StartupPanel.add(tableroJuego[8][4]);
		this.coordenadas.put(tableroJuego[8][4], new Coordenada(8,4));

		tableroJuego[8][5] = new JLabel();
		tableroJuego[8][5].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, tableroJuego[8][5]);
			}
		});
		tableroJuego[8][5].setBounds(550, 562, 48, 49);
		StartupPanel.add(tableroJuego[8][5]);
		this.coordenadas.put(tableroJuego[8][5], new Coordenada(8,5));

		tableroJuego[8][6] = new JLabel();
		tableroJuego[8][6].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, tableroJuego[8][6]);
			}
		});
		tableroJuego[8][6].setBounds(423, 624, 48, 50);
		StartupPanel.add(tableroJuego[8][6]);
		this.coordenadas.put(tableroJuego[8][6], new Coordenada(8,6));

		tableroJuego[8][7] = new JLabel();
		tableroJuego[8][7].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, tableroJuego[8][7]);
			}
		});
		tableroJuego[8][7].setBounds(484, 623, 48, 49);
		StartupPanel.add(tableroJuego[8][7]);
		this.coordenadas.put(tableroJuego[8][7], new Coordenada(8,7));

		tableroJuego[8][8] = new JLabel();
		tableroJuego[8][8].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, tableroJuego[8][8]);
			}
		});
		tableroJuego[8][8].setBounds(550, 624, 48, 49);
		StartupPanel.add(tableroJuego[8][8]);
		this.coordenadas.put(tableroJuego[8][8], new Coordenada(8,8));

		
		
		
		
		/*-------------------------------------------------------------------------------------*/
		
		
		
		mapLabel = new JLabel();
		mapLabel.setBackground(Color.WHITE);
		mapLabel.setIcon(new ImageIcon(
					this.getClass().getClassLoader().getResource("image/boarde.png")));
		mapLabel.setBounds(39, 126, 564, 556);
		mapLabel.setOpaque(true);
				
		StartupPanel.add(mapLabel);
		
		ultimate = new JLabel();
		ultimate.setIcon(new ImageIcon(GameWindow.class.getResource("/image/ultimate.png")));
		ultimate.setBounds(12, 28, 459, 73);
		StartupPanel.add(ultimate);
		
		tictactoe = new JLabel();
		tictactoe.setIcon(new ImageIcon(GameWindow.class.getResource("/image/tictactoe.png")));
		tictactoe.setBounds(466, 42, 496, 53);
		StartupPanel.add(tictactoe);
		
		player1 = new JLabel();
		player2 = new JLabel();
		if (self.equals(retador)) {
			player1.setIcon(new ImageIcon(GameWindow.class.getResource("/image/player1.png")));
			player2.setIcon(new ImageIcon(GameWindow.class.getResource("/image/player2.png")));
		} else {
			player1.setIcon(new ImageIcon(GameWindow.class.getResource("/image/player2.png")));
			player2.setIcon(new ImageIcon(GameWindow.class.getResource("/image/player1.png")));
		}
		player1.setBounds(697, 119, 65, 60);
		StartupPanel.add(player1);		
		player2.setBounds(697, 191, 55, 53);
		StartupPanel.add(player2);
		
		globalview = new JLabel("Vista Global");
		globalview.setForeground(new Color(0, 100, 0));
		globalview.setFont(new Font("Dialog", Font.BOLD, 24));
		globalview.setHorizontalAlignment(SwingConstants.CENTER);
		globalview.setBounds(697, 364, 192, 25);
		StartupPanel.add(globalview);
		
		ply1 = new JLabel("Player 1");
		ply1.setForeground(new Color(0, 100, 0));
		ply1.setFont(new Font("Dialog", Font.BOLD, 16));
		ply1.setBounds(618, 138, 78, 30);
		StartupPanel.add(ply1);
		
		ply2 = new JLabel("Player 2");
		ply2.setForeground(new Color(0, 100, 0));
		ply2.setFont(new Font("Dialog", Font.BOLD, 16));
		ply2.setBounds(618, 209, 81, 19);
		StartupPanel.add(ply2);
		
		notification = new JLabel();
		notification.setHorizontalAlignment(SwingConstants.CENTER);
		notification.setFont(new Font("Dialog", Font.BOLD, 16));
		notification.setBounds(618, 253, 344, 99);
		notification.setText("<html>angel puso en T1 su ficha en la casilla 2,1 : toni tiene que poner en T4 su ficha</html>");
		StartupPanel.add(notification);
		
		ply1name = new JLabel(retador);
		ply1name.setForeground(new Color(0, 100, 0));
		ply1name.setFont(new Font("Dialog", Font.BOLD, 18));
		ply1name.setHorizontalAlignment(SwingConstants.CENTER);
		ply1name.setBounds(751, 138, 106, 31);
		StartupPanel.add(ply1name);
		
		ply2name = new JLabel(retado);
		ply2name.setFont(new Font("Dialog", Font.BOLD, 18));
		ply2name.setForeground(new Color(0, 100, 0));
		ply2name.setHorizontalAlignment(SwingConstants.CENTER);
		ply2name.setBounds(764, 206, 92, 25);
		StartupPanel.add(ply2name);
		
		/*--------------------------TABLERO GLOBAL-----------------------------------------------*/
		F1 = new JLabel();
		F1.setBounds(667, 413, 78, 76);
		StartupPanel.add(F1);
		this.tableroGlobal[0][0] = F1;
		F2 = new JLabel();
		F2.setBounds(667, 501, 78, 79);
		StartupPanel.add(F2);
		this.tableroGlobal[0][1] = F2;

		F3 = new JLabel();
		F3.setBounds(667, 592, 78, 81);
		StartupPanel.add(F3);
		this.tableroGlobal[0][2] = F3;

		F4 = new JLabel();
		F4.setBounds(759, 413, 78, 76);
		StartupPanel.add(F4);
		this.tableroGlobal[1][0] = F4;

		F5 = new JLabel();
		F5.setBounds(759, 501, 78, 79);
		StartupPanel.add(F5);
		this.tableroGlobal[1][1] = F5;

		F6 = new JLabel();
		F6.setBounds(759, 592, 78, 81);
		StartupPanel.add(F6);
		this.tableroGlobal[1][2] = F6;

		F7 = new JLabel();
		F7.setBounds(846, 413, 78, 76);
		StartupPanel.add(F7);
		this.tableroGlobal[2][0] = F7;

		F8 = new JLabel();
		F8.setBounds(846, 506, 78, 74);
		StartupPanel.add(F8);
		this.tableroGlobal[2][1] = F8;

		F9 = new JLabel();
		F9.setBounds(846, 592, 78, 81);
		StartupPanel.add(F9);
		this.tableroGlobal[2][2] = F9;

		
		minimapLabel = new JLabel();
		minimapLabel.setIcon(new ImageIcon(GameWindow.class.getResource("/image/miniboard.png")));
		minimapLabel.setBounds(644, 395, 299, 296);
		StartupPanel.add(minimapLabel);
		
		turnoPly1 = new JLabel();
		turnoPly1.setIcon(new ImageIcon(GameWindow.class.getResource("/image/arrow.png")));
		turnoPly1.setBounds(869, 126, 70, 45);
		StartupPanel.add(turnoPly1);
		
		turnoPly2 = new JLabel();
		turnoPly2.setIcon(new ImageIcon(GameWindow.class.getResource("/image/arrow.png")));
		turnoPly2.setBounds(868, 203, 70, 37);
		turnoPly2.setVisible(false);
		StartupPanel.add(turnoPly2);
		this.setLocationRelativeTo(null);
		
		setDefaultCloseOperation (WindowConstants.DO_NOTHING_ON_CLOSE);
		
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent we){
				int eleccion = JOptionPane.showConfirmDialog(null, "Desea salir?");
				if ( eleccion == 0) {
					we.getWindow().dispose();
					//hilo
					CerrarJuegoThread thread = new CerrarJuegoThread();
					thread.start();
				}  
			}
		});
	}
	//Evento para pintar las fichas, primero se mira si ya se ha pinchado sobre esa casilla
	 private void FichaMouseClicked(java.awt.event.MouseEvent e, JLabel j) {
		 System.out.println(this.coordenadas.get(j).toString());
		 System.out.println("COMPROBAR MOVIMIENTO VALIDO");
		 int cTableroG,fTableroG, cTableroP, fTableroP;
		cTableroG = this.coordenadas.get(j).getx() % 3;
		fTableroG = (int)(this.coordenadas.get(j).getx() / 3);
		cTableroP = this.coordenadas.get(j).gety() % 3 ;
		fTableroP = (int)(this.coordenadas.get(j).gety() / 3);
		 
		try {
			Controller cntrl;
			cntrl = Controller.get();
			cntrl.ponerMovimiento(cTableroG, fTableroG, cTableroP, fTableroP);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		System.out.println(this.coordenadas.get(j).toString());
		System.out.println("cTG : "+cTableroG+" fTG : "+fTableroG+" cTp: "+cTableroP+ " fTp : "+fTableroP);
		 
	 }

	@Override
	public void partidaFinalizada(String email) {
		System.out.println("GANADOR : "+email);
		PartidaFinalizadaThread thread = new PartidaFinalizadaThread(email, this);
		thread.start();
	}

		 


	@Override
	public void ponerFicha(String email, int cT, int fT, int cC, int fC) {
		// TODO Auto-generated method stub
		System.out.println("En poner ficha: " + cT + " " + fT + " " + cC + " " + fC);
		if (email.equals(this.ply1name.getText()))
			this.tableroJuego[cT + fT * 3][cC + fC * 3].setIcon(new ImageIcon(GameWindow.class.getResource("/image/o.png")));
		else
			this.tableroJuego[cT + fT * 3][cC + fC * 3].setIcon(new ImageIcon(GameWindow.class.getResource("/image/x.png")));
		this.cambiarTurno();
	}

	public void cambiarTurno() {
		if (this.turnoPly1.isVisible()) {
			this.turnoPly1.setVisible(false);
			this.turnoPly2.setVisible(true);
		} else {
			this.turnoPly1.setVisible(true);
			this.turnoPly2.setVisible(false);			
		}		
	}
	

	@Override
	public void cerrar() {
		this.dispose();
	}

	@Override
	public void cerrarPorAbandonoOponente() {
		JOptionPane.showMessageDialog(null, "El oponente ha abandonado la partida.");
		this.dispose();
	}

	@Override
	public void tableroGanado(String email, int col, int fila) {
		System.out.println("Tablero ganado col[" + col + "] fila[" + fila + "] interfaz: " + this.ply1name.getText() + " " + email);
		if (email.equals(this.ply1name.getText()))
			this.tableroGlobal[col][fila].setIcon(new ImageIcon(GameWindow.class.getResource("/image/o.png")));
		else
			this.tableroGlobal[col][fila].setIcon(new ImageIcon(GameWindow.class.getResource("/image/x.png")));
	}


}

class CerrarJuegoThread extends Thread {
	@Override
	public void run() {
		try {
			Controller ctrl = Controller.get();
			ctrl.cerrarPartida();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
class PartidaFinalizadaThread extends Thread {
	String email;
	GameWindow gw;
	
	PartidaFinalizadaThread(String email, GameWindow gw) {
		this.email = email;
		this.gw = gw;
	}
	
	
	@Override
	public void run() {
		try {
			JOptionPane.showMessageDialog(null, this.email + " ha ganado la partida.");
			this.gw.dispose();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
}

