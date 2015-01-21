/**********************************************************************
Super Tic-Tac-Toe Board Game.  The user is able to choose if X or O
is the first player and also the size of the board (2x2 up to 15x15).

@author Conner Toney
@version GVSU Fall 2014
 *********************************************************************/
package package1;

import javax.swing.*;

public class SuperTicTacToe {

	/** input board size as string */
	private static String pSizeStr;

	/** input board size as int */
	private static int pSize;

	/** input first player as string */
	private static String pPlayerStr;

	/** input first player as int */
	private static int pPlayer;

	/******************************************************************
	 Main method - used to create the entire board game
	 *****************************************************************/
	public static void main(String[] args) {

		//instantiates new frame
		JFrame frame = new JFrame("Super TicTacToe");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//asks for input for board size
		pSizeStr = JOptionPane.showInputDialog("Board size: ");

		//if the input is not a two digit number, the option is given
		//to either try again or exit
		if (!pSizeStr.matches("[0-9]+")) {
			do {
				int askSizeAgain = JOptionPane.showConfirmDialog(null,
						"Invalid input. Try again?",
						"Invalid input.",
						JOptionPane.YES_NO_OPTION);
				if (askSizeAgain == JOptionPane.YES_OPTION)
					pSizeStr = JOptionPane.showInputDialog(""
							+ "Board size: ");
				if (askSizeAgain == JOptionPane.NO_OPTION)
					System.exit(0);
			} while (!pSizeStr.matches("[0-9]+"));
		}

		//switches the input from String to int
		pSize = Integer.parseInt(pSizeStr);

		//if the input number is below 2 or above 15, the user is asked
		//to either reenter a number or exit
		do {
			if (pSize < 2 || pSize > 15) {
				int selection = JOptionPane.showConfirmDialog(null,
						"Numbers must be between 2 and 15. Try again?",
						"Invalid input.",
						JOptionPane.YES_NO_OPTION);

				//if yes, asks for input
				if (selection == JOptionPane.YES_OPTION) {
					pSizeStr = JOptionPane.showInputDialog("" + 
							"Board size: ");
					pSize = Integer.parseInt(pSizeStr);
				}

				//if no, exits program
				if (selection == JOptionPane.NO_OPTION) {
					System.exit(0);
				}
			}
		} while (pSize < 2 || pSize > 15);

		//asks if X or O will start first
		pPlayerStr = JOptionPane.showInputDialog("Who starts?"
				+ " X or O: ");
		if (pPlayerStr.equals("X") || pPlayerStr.equals("x"))
			pPlayer = 1;
		else if (pPlayerStr.equals("O") || pPlayerStr.equals("o"))
			pPlayer = 2;
		else {

			//if the input is invalid, X is player 1 by default
			JOptionPane.showMessageDialog(null,
					"Incorrect input. Player 1 will be X.",
					"Incorrect input!",
					JOptionPane.ERROR_MESSAGE);
			pPlayer = 1;
		}

		//creates new panel object, using pSize as param for board size
		// and pPlayer as param for starting player
		SuperTicTacToePanel panel = new SuperTicTacToePanel(pSize, 
				pPlayer);
		frame.getContentPane().add(panel);

		//frame size is based on how many tiles there are
		frame.setSize((50 * pSize) + 200, (50 * pSize) + 100);
		frame.setVisible(true);
	}
}
