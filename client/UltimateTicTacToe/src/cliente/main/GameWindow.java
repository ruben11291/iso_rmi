package cliente.main;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.border.Border;

public class GameWindow extends JFrame implements MouseListener, MouseMotionListener {
	JLayeredPane layeredPane;
	ImagePanel gameBoard;
	JPanel infoPanel;
	JLabel chessPiece;
	int xAdjustment;
	int yAdjustment;
	
	public GameWindow(){
		Dimension boardSize = new Dimension(600, 600);
		// Use a Layered Pane for this this application
		layeredPane = new JLayeredPane();
		getContentPane().add(layeredPane);
		layeredPane.setPreferredSize( new Dimension(900, 600) );
		layeredPane.addMouseListener(this);
		layeredPane.addMouseMotionListener(this);
		
		infoPanel = new JPanel();
		layeredPane.add(infoPanel, JLayeredPane.DEFAULT_LAYER);
		infoPanel.setLayout( new GridLayout(4, 1) );
		infoPanel.setPreferredSize( new Dimension(300, 600) );
		infoPanel.setBounds(600, 0, 300, 600);
		infoPanel.setBackground(Color.gray);
		
		for (int i = 0; i < 4; i++) {
			JPanel info = new JPanel( new BorderLayout() );
			info.setBorder(BorderFactory.createLineBorder(Color.black, 1));
			infoPanel.add( info );
		}
		
		JPanel info = (JPanel)infoPanel.getComponent(0);
		info.add(new JLabel("ÁNGEL"));
		info = (JPanel)infoPanel.getComponent(1);
		info.add(new JLabel("ES"));
		info = (JPanel)infoPanel.getComponent(2);
		info.add(new JLabel("BASTANTE"));
		info = (JPanel)infoPanel.getComponent(3);
		info.add(new JLabel("GAY"));
		
		//Add a chess board to the Layered Pane
		gameBoard = new ImagePanel( "src/image/board600x600.png", 9000, 900);
		layeredPane.add(gameBoard, JLayeredPane.DEFAULT_LAYER);
		gameBoard.setLayout( new GridLayout(9, 9) );
		gameBoard.setPreferredSize( boardSize );
		gameBoard.setBounds(0, 0, boardSize.width, boardSize.height);
	
		for (int i = 0; i < 81; i++) {
			JPanel square = new JPanel( new BorderLayout() );
			//square.setBorder(BorderFactory.createLineBorder(Color.black, 1));
			gameBoard.add( square );
			square.setOpaque(false);
		}
	//	Add a few pieces to the board
		JLabel piece = new JLabel( new ImageIcon("/home/toni/o.png") );
		piece.setBorder(BorderFactory.createLineBorder(Color.green, 3));
		JPanel panel = (JPanel)gameBoard.getComponent(0);
		panel.add(piece);
		piece = new JLabel(new ImageIcon("/home/toni/o.png"));
		panel = (JPanel)gameBoard.getComponent(15);
		panel.add(piece);
		piece = new JLabel(new ImageIcon("/home/toni/o.png"));
		panel = (JPanel)gameBoard.getComponent(16);
		panel.add(piece);
		piece = new JLabel(new ImageIcon("/home/toni/o.png"));
		panel = (JPanel)gameBoard.getComponent(20);
		panel.add(piece);
	 
	}
	public void mousePressed(MouseEvent e){
		chessPiece = null;
		Component c = gameBoard.findComponentAt(e.getX(), e.getY());
		if (c instanceof JPanel)
		return;
		Point parentLocation = c.getParent().getLocation();
		xAdjustment = parentLocation.x - e.getX();
		yAdjustment = parentLocation.y - e.getY();
		chessPiece = (JLabel)c;
		chessPiece.setLocation(e.getX() + xAdjustment, e.getY() + yAdjustment);
		chessPiece.setSize(chessPiece.getWidth(), chessPiece.getHeight());
		layeredPane.add(chessPiece, JLayeredPane.DRAG_LAYER);
	}
	//Move the chess piece around
	public void mouseDragged(MouseEvent me) {
		if (chessPiece == null) return;
		chessPiece.setLocation(me.getX() + xAdjustment, me.getY() + yAdjustment);
	}
	//Drop the chess piece back onto the chess board
	public void mouseReleased(MouseEvent e) {
		if(chessPiece == null) return;
			chessPiece.setVisible(false);
			Component c = gameBoard.findComponentAt(e.getX(), e.getY());
		if (c instanceof JLabel){
			Container parent = c.getParent();
			parent.remove(0);
			parent.add( chessPiece );
		}
		else {
			Container parent = (Container)c;
			parent.add( chessPiece );
		}
		chessPiece.setVisible(true);
	}
	public void mouseClicked(MouseEvent e) {
	}
	public void mouseMoved(MouseEvent e) {
	}
	public void mouseEntered(MouseEvent e){
	}
	public void mouseExited(MouseEvent e) {
	}
	public static void main(String[] args) {
		JFrame frame = new GameWindow();
		frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE );
		frame.pack();
		frame.setResizable(true);
		frame.setLocationRelativeTo( null );
		frame.setVisible(true);
	}
}

class ImagePanel extends JPanel {

	  private Image img;

	  public ImagePanel(String img) {
	    this(new ImageIcon(img).getImage());
	  }

	  public ImagePanel(String img, int width, int height) {
	    this(new ImageIcon(img).getImage(), width, height);
	  }
	  
	  public ImagePanel(Image img) {
	    this.img = img;
	    Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
	    setPreferredSize(size);
	    setMinimumSize(size);
	    setMaximumSize(size);
	    setSize(size);
	    setLayout(null);
	  }

	  public ImagePanel(Image img, int width, int height) {
	    this.img = img;
	    Dimension size = new Dimension(width, height);
	    setPreferredSize(size);
	    setMinimumSize(size);
	    setMaximumSize(size);
	    setSize(size);
	    setLayout(null);
	  }

	  public void paintComponent(Graphics g) {
	    g.drawImage(img, 0, 0, null);
	  }

	}
