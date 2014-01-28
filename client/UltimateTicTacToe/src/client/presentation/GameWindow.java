package client.presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.SwingConstants;

import client.controller.Controller;

import java.awt.Font;

public class GameWindow extends JFrame implements IJuego {

	private static final long serialVersionUID = -5128501222928885944L;

	private JPanel StartupPanel;
	private Random a= new Random();
	private JLabel mapLabel;
	private java.util.ArrayList<JLabel> pulsaciones = new java.util.ArrayList<JLabel>();

	private JLabel C1_1_1, C1_1_2, C1_1_3, C1_2_1, C1_2_2, C1_2_3, C1_3_1, C1_3_2, C1_3_3; //TABLERO 1
	private JLabel C2_1_1, C2_1_2, C2_1_3, C2_2_1, C2_2_2, C2_2_3, C2_3_1, C2_3_2, C2_3_3; //TABLERO 2
	private JLabel C3_1_1, C3_1_2, C3_1_3, C3_2_1, C3_2_2, C3_2_3, C3_3_1, C3_3_2, C3_3_3; //TABLERO 3
	private JLabel C4_1_1, C4_1_2, C4_1_3, C4_2_1, C4_2_2, C4_2_3, C4_3_1, C4_3_2, C4_3_3; //TABLERO 4
	private JLabel C5_1_1, C5_1_2, C5_1_3, C5_2_1, C5_2_2, C5_2_3, C5_3_1, C5_3_2, C5_3_3; //TABLERO 5
	private JLabel C6_1_1, C6_1_2, C6_1_3, C6_2_1, C6_2_2, C6_2_3, C6_3_1, C6_3_2, C6_3_3; //TABLERO 6
	private JLabel C7_1_1, C7_1_2, C7_1_3, C7_2_1, C7_2_2, C7_2_3, C7_3_1, C7_3_2, C7_3_3; //TABLERO 7
	private JLabel C8_1_1, C8_1_2, C8_1_3, C8_2_1, C8_2_2, C8_2_3, C8_3_1, C8_3_2, C8_3_3; //TABLERO 8
	private JLabel C9_1_1, C9_1_2, C9_1_3, C9_2_1, C9_2_2, C9_2_3, C9_3_1, C9_3_2, C9_3_3; //TABLERO 9
	
	private JLabel F1, F2, F3, F4, F5, F6, F7, F8, F9; //TABLERO GLOBAL

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

	
	public GameWindow() {
		super();
		try {
			Controller cntrl;
			cntrl = Controller.get();
			cntrl.setJuego(this);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		this.initGUI();
	}

	private void initGUI() {
		
		this.setResizable(false);
		this.setTitle("Ultimate Tic-Tac-Toe - GAME");
		this.setSize(980, 730);

		StartupPanel = new JPanel();
		StartupPanel.setBackground(UIManager.getColor("Button.select"));
		this.getContentPane().add(StartupPanel, BorderLayout.CENTER);
		StartupPanel.setLayout(null);
		
		/*----------------------------- Casillas TABLERO 1 -----------------------------------------*/
		C1_1_1 = new JLabel();
		C1_1_1.setBackground(Color.WHITE);
		C1_1_1.setBounds(49, 126, 48, 53);
		C1_1_1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                FichaMouseClicked(evt, C1_1_1);
            }
        });
		StartupPanel.add(C1_1_1);
		
		C1_1_2 = new JLabel();
		C1_1_2.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, C1_1_2);
			}
		});
		C1_1_2.setBackground(UIManager.getColor("Button.WITHE"));
		C1_1_2.setBounds(109, 126, 53, 53);
		StartupPanel.add(C1_1_2);
		
		C1_1_3 = new JLabel();
		C1_1_3.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, C1_1_3);
			}
		});
		C1_1_3.setBackground(UIManager.getColor("Button.WHITE"));
		C1_1_3.setBounds(175, 126, 48, 53);
		StartupPanel.add(C1_1_3);
		
		C1_2_1 = new JLabel();
		C1_2_1.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, C1_2_1);
			}
		});
		C1_2_1.setBackground(UIManager.getColor("Button.WHITE"));
		C1_2_1.setBounds(49, 191, 48, 53);
		StartupPanel.add(C1_2_1);
		
		C1_2_2 = new JLabel();
		C1_2_2.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, C1_2_2);
			}
		});
		C1_2_2.setBackground(UIManager.getColor("Button.WHITE"));
		C1_2_2.setBounds(113, 195, 53, 49);
		StartupPanel.add(C1_2_2);
		
		C1_2_3= new JLabel();
		C1_2_3.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, C1_2_3);
			}
		});
		C1_2_3.setBackground(UIManager.getColor("Button.WHITE"));
		C1_2_3.setBounds(175, 191, 48, 53);
		StartupPanel.add(C1_2_3);
		
		C1_3_1 = new JLabel();
		C1_3_1.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, C1_3_1);
			}
		});
		C1_3_1.setBounds(49, 253, 55, 53);
		StartupPanel.add(C1_3_1);
		
		C1_3_2 = new JLabel();
		C1_3_2.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, C1_3_2);
			}
		});
		C1_3_2.setBounds(109, 312, 53, 54);
		StartupPanel.add(C1_3_2);
		
		C1_3_3 = new JLabel();
		C1_3_3.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, C1_3_3);
			}
		});
		C1_3_3.setBounds(175, 256, 48, 50);
		StartupPanel.add(C1_3_3);
		/*-------------------------------------------------------------------------------------*/
		
		/*-------------------------------Casillas TABLERO 2 -------------------------------------------*/
		C2_1_1 = new JLabel();
		C2_1_1.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, C2_1_1);
			}
		});
		C2_1_1.setBounds(235, 131, 53, 48);
		StartupPanel.add(C2_1_1);
		
		C2_1_2 = new JLabel();
		C2_1_2.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, C2_1_2);
			}
		});
		C2_1_2.setBounds(295, 131, 53, 48);
		StartupPanel.add(C2_1_2);
		
		C2_1_3 = new JLabel();
		C2_1_3.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, C2_1_3);
			}
		});
		C2_1_3.setBounds(362, 131, 48, 48);
		StartupPanel.add(C2_1_3);
		
		C2_2_1 = new JLabel();
		C2_2_1.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, C2_2_1);
			}
		});
		C2_2_1.setBounds(235, 191, 53, 53);
		StartupPanel.add(C2_2_1);
		
		C2_2_2 = new JLabel();
		C2_2_2.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, C2_2_2);
			}
		});
		C2_2_2.setBounds(296, 191, 55, 53);
		StartupPanel.add(C2_2_2);
		
		C2_2_3 = new JLabel();
		C2_2_3.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, C2_2_3);
			}
		});
		C2_2_3.setBounds(362, 191, 48, 53);
		StartupPanel.add(C2_2_3);
		
		C2_3_1 = new JLabel();
		C2_3_1.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, C2_3_1);
			}
		});
		C2_3_1.setBounds(235, 256, 53, 49);
		StartupPanel.add(C2_3_1);
		
		C2_3_2 = new JLabel();
		C2_3_2.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, C2_3_2);
			}
		});
		C2_3_2.setBounds(295, 252, 53, 53);
		StartupPanel.add(C2_3_2);
		
		C2_3_3 = new JLabel();
		C2_3_3.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, C2_3_3);
			}
		});
		C2_3_3.setBounds(365, 256, 47, 49);
		StartupPanel.add(C2_3_3);
		/*-------------------------------------------------------------------------------------*/
		
		/*--------------------------------Casillas TABLERO 3-----------------------------------*/
		
		C3_1_1 = new JLabel();
		C3_1_1.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, C3_1_1);
			}
		});
		C3_1_1.setBounds(423, 131, 48, 48);
		StartupPanel.add(C3_1_1);
		
		C3_1_2 = new JLabel();
		C3_1_2.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, C3_1_2);
			}
		});
		C3_1_2.setBounds(478, 133, 53, 46);
		StartupPanel.add(C3_1_2);
		
		C3_1_3 = new JLabel();
		C3_1_3.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, C3_1_3);
			}
		});
		C3_1_3.setBounds(545, 131, 48, 48);
		StartupPanel.add(C3_1_3);
		
		C3_2_1 = new JLabel();
		C3_2_1.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, C3_2_1);
			}
		});
		C3_2_1.setBounds(422, 191, 49, 49);
		StartupPanel.add(C3_2_1);
		
		C3_2_2 = new JLabel();
		C3_2_2.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				FichaMouseClicked(e, C3_2_2);
			}
		});
		C3_2_2.setBounds(484, 194, 48, 50);
		StartupPanel.add(C3_2_2);
		
		C3_2_3 = new JLabel();
		C3_2_3.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, C3_2_3);
			}
		});
		C3_2_3.setBounds(545, 191, 48, 53);
		StartupPanel.add(C3_2_3);
		
		C3_3_1 = new JLabel();
		C3_3_1.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, C3_3_1);
			}
		});
		C3_3_1.setBounds(424, 253, 42, 53);
		StartupPanel.add(C3_3_1);
		
		C3_3_2 = new JLabel();
		C3_3_2.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, C3_3_2);
			}
		});
		C3_3_2.setBounds(482, 259, 55, 47);
		StartupPanel.add(C3_3_2);
		
		C3_3_3 = new JLabel();
		C3_3_3.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, C3_3_3);
			}
		});
		C3_3_3.setBounds(550, 256, 48, 50);
		StartupPanel.add(C3_3_3);
		/*-------------------------------------------------------------------------------------*/
		
		/*--------------------------------Casillas TABLERO 4-----------------------------------*/
		C4_1_1 = new JLabel();
		C4_1_1.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, C4_1_1);
			}
		});
		C4_1_1.setBounds(49, 317, 44, 49);
		StartupPanel.add(C4_1_1);
		
		C4_1_2 = new JLabel();
		C4_1_2.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, C4_1_2);
			}
		});
		C4_1_2.setBounds(109, 256, 55, 50);
		StartupPanel.add(C4_1_2);
		
		C4_1_3 = new JLabel();
		C4_1_3.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, C4_1_3);
			}
		});
		C4_1_3.setBounds(175, 318, 48, 49);
		StartupPanel.add(C4_1_3);
		
		C4_2_1 = new JLabel();
		C4_2_1.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, C4_2_1);
			}
		});
		C4_2_1.setBounds(49, 379, 48, 49);
		StartupPanel.add(C4_2_1);
		
		C4_2_2 = new JLabel();
		C4_2_2.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, C4_2_2);
			}
		});
		C4_2_2.setBounds(109, 379, 53, 49);
		StartupPanel.add(C4_2_2);
		
		C4_2_3 = new JLabel();
		C4_2_3.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, C4_2_3);
			}
		});
		C4_2_3.setBounds(175, 379, 48, 49);
		StartupPanel.add(C4_2_3);
		
		C4_3_1 = new JLabel();
		C4_3_1.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, C4_3_1);
			}
		});
		C4_3_1.setBounds(49, 439, 48, 50);
		StartupPanel.add(C4_3_1);
		
		C4_3_2 = new JLabel();
		C4_3_2.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, C4_3_2);
			}
		});
		C4_3_2.setBounds(109, 440, 48, 49);
		StartupPanel.add(C4_3_2);
		
		C4_3_3 = new JLabel();
		C4_3_3.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, C4_3_3);
			}
		});
		C4_3_3.setBounds(175, 440, 48, 49);
		StartupPanel.add(C4_3_3);
		/*----------------------------------------------------------------------------------*/
		
		/*--------------------------------Casillas TABLERO 5-----------------------------------*/
		C5_1_1 = new JLabel();
		C5_1_1.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, C5_1_1);
			}
		});
		C5_1_1.setBounds(235, 317, 44, 49);
		StartupPanel.add(C5_1_1);
		
		C5_1_2 = new JLabel();
		C5_1_2.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, C5_1_2);
			}
		});
		C5_1_2.setBounds(295, 317, 55, 50);
		StartupPanel.add(C5_1_2);
		
		C5_1_3 = new JLabel();
		C5_1_3.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, C5_1_3);
			}
		});
		C5_1_3.setBounds(362, 318, 48, 49);
		StartupPanel.add(C5_1_3);
		
		C5_2_1 = new JLabel();
		C5_2_1.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, C5_2_1);
			}
		});
		C5_2_1.setBounds(235, 379, 48, 49);
		StartupPanel.add(C5_2_1);
		
		C5_2_2 = new JLabel();
		C5_2_2.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, C5_2_2);
			}
		});
		C5_2_2.setBounds(295, 379, 53, 49);
		StartupPanel.add(C5_2_2);
		
		C5_2_3 = new JLabel();
		C5_2_3.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, C5_2_3);
			}
		});
		C5_2_3.setBounds(362, 379, 48, 49);
		StartupPanel.add(C5_2_3);
		
		C5_3_1 = new JLabel();
		C5_3_1.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, C5_3_1);
			}
		});
		C5_3_1.setBounds(235, 439, 48, 50);
		StartupPanel.add(C5_3_1);
		
		C5_3_2 = new JLabel();
		C5_3_2.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, C5_3_2);
			}
		});
		C5_3_2.setBounds(300, 440, 48, 49);
		StartupPanel.add(C5_3_2);
		
		C5_3_3 = new JLabel();
		C5_3_3.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, C5_3_3);
			}
		});
		C5_3_3.setBounds(362, 440, 48, 49);
		StartupPanel.add(C5_3_3);
		/*-------------------------------------------------------------------------------------*/
		
		/*--------------------------------Casillas TABLERO 6-----------------------------------*/
		C6_1_1 = new JLabel();
		C6_1_1.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, C6_1_1);
			}
		});
		C6_1_1.setBounds(423, 317, 44, 49);
		StartupPanel.add(C6_1_1);
		
		C6_1_2 = new JLabel();
		C6_1_2.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, C6_1_2);
			}
		});
		C6_1_2.setBounds(484, 318, 55, 50);
		StartupPanel.add(C6_1_2);
		
		C6_1_3 = new JLabel();
		C6_1_3.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, C6_1_3);
			}
		});
		C6_1_3.setBounds(545, 317, 48, 49);
		StartupPanel.add(C6_1_3);
		
		C6_2_1 = new JLabel();
		C6_2_1.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, C6_2_1);
			}
		});
		C6_2_1.setBounds(423, 379, 48, 49);
		StartupPanel.add(C6_2_1);
		
		C6_2_2 = new JLabel();
		C6_2_2.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, C6_2_2);
			}
		});
		C6_2_2.setBounds(484, 379, 53, 49);
		StartupPanel.add(C6_2_2);
		
		C6_2_3 = new JLabel();
		C6_2_3.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, C6_2_3);
			}
		});
		C6_2_3.setBounds(550, 379, 48, 49);
		StartupPanel.add(C6_2_3);
		
		C6_3_1 = new JLabel();
		C6_3_1.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, C6_3_1);
			}
		});
		C6_3_1.setBounds(423, 439, 48, 50);
		StartupPanel.add(C6_3_1);
		
		C6_3_2 = new JLabel();
		C6_3_2.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, C6_3_2);
			}
		});
		C6_3_2.setBounds(484, 439, 48, 49);
		StartupPanel.add(C6_3_2);
		
		C6_3_3 = new JLabel();
		C6_3_3.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, C6_3_3);
			}
		});
		C6_3_3.setBounds(550, 440, 48, 49);
		StartupPanel.add(C6_3_3);
		/*--------------------------------------------------------------------------------------*/
		
		/*--------------------------------Casillas TABLERO 7-----------------------------------*/
		C7_1_1 = new JLabel();
		C7_1_1.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, C7_1_1);
			}
		});
		C7_1_1.setBounds(49, 501, 44, 49);
		StartupPanel.add(C7_1_1);
		
		C7_1_2 = new JLabel();
		C7_1_2.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, C7_1_2);
			}
		});
		C7_1_2.setBounds(105, 500, 55, 50);
		StartupPanel.add(C7_1_2);
		
		C7_1_3 = new JLabel();
		C7_1_3.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, C7_1_3);
			}
		});
		C7_1_3.setBounds(172, 501, 48, 49);
		StartupPanel.add(C7_1_3);
		
		C7_2_1 = new JLabel();
		C7_2_1.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, C7_2_1);
			}
		});
		C7_2_1.setBounds(49, 562, 48, 49);
		StartupPanel.add(C7_2_1);
		
		C7_2_2 = new JLabel();
		C7_2_2.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, C7_2_2);
			}
		});
		C7_2_2.setBounds(105, 562, 53, 49);
		StartupPanel.add(C7_2_2);
		
		C7_2_3 = new JLabel();
		C7_2_3.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, C7_2_3);
			}
		});
		C7_2_3.setBounds(172, 562, 48, 49);
		StartupPanel.add(C7_2_3);
		
		C7_3_1 = new JLabel();
		C7_3_1.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, C7_3_1);
			}
		});
		C7_3_1.setBounds(49, 623, 48, 50);
		StartupPanel.add(C7_3_1);
		
		C7_3_2 = new JLabel();
		C7_3_2.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, C7_3_2);
			}
		});
		C7_3_2.setBounds(109, 624, 48, 49);
		StartupPanel.add(C7_3_2);
		
		C7_3_3 = new JLabel();
		C7_3_3.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, C7_3_3);
			}
		});
		C7_3_3.setBounds(175, 624, 48, 49);
		StartupPanel.add(C7_3_3);
		/*-------------------------------------------------------------------------------------*/
		
		/*--------------------------------Casillas TABLERO 8-----------------------------------*/
		C8_1_1 = new JLabel();
		C8_1_1.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, C8_1_1);
			}
		});
		C8_1_1.setBounds(235, 501, 44, 49);
		StartupPanel.add(C8_1_1);
		
		C8_1_2 = new JLabel();
		C8_1_2.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, C8_1_2);
			}
		});
		C8_1_2.setBounds(293, 501, 55, 50);
		StartupPanel.add(C8_1_2);
		
		C8_1_3 = new JLabel();
		C8_1_3.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, C8_1_3);
			}
		});
		C8_1_3.setBounds(362, 501, 48, 49);
		StartupPanel.add(C8_1_3);
		
		C8_2_1 = new JLabel();
		C8_2_1.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, C8_2_1);
			}
		});
		C8_2_1.setBounds(240, 562, 48, 49);
		StartupPanel.add(C8_2_1);
		
		C8_2_2 = new JLabel();
		C8_2_2.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, C8_2_2);
			}
		});
		C8_2_2.setBounds(295, 562, 53, 49);
		StartupPanel.add(C8_2_2);
		
		C8_2_3 = new JLabel();
		C8_2_3.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, C8_2_3);
			}
		});
		C8_2_3.setBounds(362, 562, 48, 49);
		StartupPanel.add(C8_2_3);
		
		C8_3_1 = new JLabel();
		C8_3_1.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, C8_3_1);
			}
		});
		C8_3_1.setBounds(235, 623, 48, 50);
		StartupPanel.add(C8_3_1);
		
		C8_3_2 = new JLabel();
		C8_3_2.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, C8_3_2);
			}
		});
		C8_3_2.setBounds(300, 623, 48, 49);
		StartupPanel.add(C8_3_2);
		
		C8_3_3 = new JLabel();
		C8_3_3.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, C8_3_3);
			}
		});
		C8_3_3.setBounds(362, 624, 48, 49);
		StartupPanel.add(C8_3_3);
		/*-------------------------------------------------------------------------------------*/
		
		/*--------------------------------Casillas TABLERO 9-----------------------------------*/
		C9_1_1 = new JLabel();
		C9_1_1.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, C9_1_1);
			}
		});
		C9_1_1.setBounds(423, 501, 44, 49);
		StartupPanel.add(C9_1_1);
		
		C9_1_2 = new JLabel();
		C9_1_2.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, C9_1_2);
			}
		});
		C9_1_2.setBounds(482, 500, 55, 50);
		StartupPanel.add(C9_1_2);
		
		C9_1_3 = new JLabel();
		C9_1_3.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, C9_1_3);
			}
		});
		C9_1_3.setBounds(545, 501, 48, 49);
		StartupPanel.add(C9_1_3);
		
		C9_2_1 = new JLabel();
		C9_2_1.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, C9_2_1);
			}
		});
		C9_2_1.setBounds(423, 562, 48, 49);
		StartupPanel.add(C9_2_1);
		
		C9_2_2 = new JLabel();
		C9_2_2.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, C9_2_2);
			}
		});
		C9_2_2.setBounds(484, 562, 53, 49);
		StartupPanel.add(C9_2_2);
		
		C9_2_3 = new JLabel();
		C9_2_3.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, C9_2_3);
			}
		});
		C9_2_3.setBounds(550, 562, 48, 49);
		StartupPanel.add(C9_2_3);
		
		C9_3_1 = new JLabel();
		C9_3_1.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, C9_3_1);
			}
		});
		C9_3_1.setBounds(423, 624, 48, 50);
		StartupPanel.add(C9_3_1);
		
		C9_3_2 = new JLabel();
		C9_3_2.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, C9_3_2);
			}
		});
		C9_3_2.setBounds(484, 623, 48, 49);
		StartupPanel.add(C9_3_2);
		
		C9_3_3 = new JLabel();
		C9_3_3.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FichaMouseClicked(e, C9_3_3);
			}
		});
		C9_3_3.setBounds(550, 624, 48, 49);
		StartupPanel.add(C9_3_3);
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
		player1.setIcon(new ImageIcon(GameWindow.class.getResource("/image/player1.png")));
		player1.setBounds(697, 119, 65, 60);
		StartupPanel.add(player1);
		
		player2 = new JLabel();
		player2.setIcon(new ImageIcon(GameWindow.class.getResource("/image/player2.png")));
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
		
		ply1name = new JLabel("Ángel");
		ply1name.setForeground(new Color(0, 100, 0));
		ply1name.setFont(new Font("Dialog", Font.BOLD, 18));
		ply1name.setHorizontalAlignment(SwingConstants.CENTER);
		ply1name.setBounds(751, 138, 106, 31);
		StartupPanel.add(ply1name);
		
		ply2name = new JLabel("Antonio");
		ply2name.setFont(new Font("Dialog", Font.BOLD, 18));
		ply2name.setForeground(new Color(0, 100, 0));
		ply2name.setHorizontalAlignment(SwingConstants.CENTER);
		ply2name.setBounds(764, 206, 92, 25);
		StartupPanel.add(ply2name);
		
		/*--------------------------TABLERO GLOBAL-----------------------------------------------*/
		F1 = new JLabel();
		F1.setBounds(667, 413, 78, 76);
		StartupPanel.add(F1);
		
		F2 = new JLabel();
		F2.setBounds(759, 413, 78, 76);
		StartupPanel.add(F2);
		
		F3 = new JLabel();
		F3.setBounds(846, 413, 78, 76);
		StartupPanel.add(F3);
		
		F4 = new JLabel();
		F4.setBounds(667, 501, 78, 79);
		StartupPanel.add(F4);
		
		F5 = new JLabel();
		F5.setBounds(759, 501, 78, 79);
		StartupPanel.add(F5);
		
		F6 = new JLabel();
		F6.setBounds(846, 506, 78, 74);
		StartupPanel.add(F6);
		
		F7 = new JLabel();
		F7.setBounds(667, 592, 78, 81);
		StartupPanel.add(F7);
		
		F8 = new JLabel();
		F8.setBounds(759, 592, 78, 81);
		StartupPanel.add(F8);
		
		F9 = new JLabel();
		F9.setBounds(846, 592, 78, 81);
		StartupPanel.add(F9);
		
		
		
		minimapLabel = new JLabel();
		minimapLabel.setIcon(new ImageIcon(GameWindow.class.getResource("/image/miniboard.png")));
		minimapLabel.setBounds(644, 395, 299, 296);
		StartupPanel.add(minimapLabel);
		
		turnoPly1 = new JLabel("");
		turnoPly1.setIcon(new ImageIcon(GameWindow.class.getResource("/image/arrow.png")));
		turnoPly1.setBounds(869, 126, 70, 45);
		StartupPanel.add(turnoPly1);
		
		turnoPly2 = new JLabel("");
		turnoPly2.setIcon(new ImageIcon(GameWindow.class.getResource("/image/arrow.png")));
		turnoPly2.setBounds(868, 203, 70, 37);
		StartupPanel.add(turnoPly2);
		this.setLocationRelativeTo(null);

	}
	//Evento para pintar las fichas, primero se mira si ya se ha pinchado sobre esa casilla
	 private void FichaMouseClicked(java.awt.event.MouseEvent e, JLabel j) {
		 
		 if(!pulsaciones.contains(j)){
		 	if(a.nextInt(2)==0){
		 		  j.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource("image/x.png")));
		 	}
		 	else{
		 		  j.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource("image/o.png")));
		 	}
		 }
		 pulsaciones.add(j);
	 }
	 
	 private void GlobalFichaMouseClicked(java.awt.event.MouseEvent e, JLabel j){
		 if(a.nextInt(2)==0){
	 		  j.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource("image/O.png")));
	 	}
	 	else{
	 		  j.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource("image/X.png")));
	 	}
	 }

	@Override
	public void hayGanador(int jugador) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ponerFicha(String email, int cT, int fT, int cC, int fC) {
		// TODO Auto-generated method stub
		
	}

}

