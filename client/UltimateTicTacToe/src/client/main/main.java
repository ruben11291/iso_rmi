package client.main;


import client.presentation.StartupWindow;

public class main {
	private static StartupWindow startupWindow = null;
	
	public static void main(String []args) throws Exception{
		startupWindow = new StartupWindow();
		startupWindow.setLocationRelativeTo(null);
		startupWindow.setVisible(true);		
	}
}
