package client.presentation;


public class UltimateTicTacToe {

	private static StartupWindow startupWindow = null;

	public static void main(String[] args) throws Exception {

		startupWindow = new StartupWindow();
		startupWindow.setLocationRelativeTo(null);
		startupWindow.setVisible(true);
	}

}
