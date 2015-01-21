/**********************************************************************
SuperTicTacToePanel is used is used to visually represent the
Super Tic-Tac-Toe game.

@author Conner Toney
@version GVSU Fall 2014
 *********************************************************************/
package package1;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class SuperTicTacToePanel extends JPanel {

	/** represents the GUI board seen by user */
	private JButton[][] board;

	/** parameter received from game object to represent board */
	private Cell[][] iBoard;

	/** used to quit game */
	private JButton quitButton;

	/** represents X as seen on GUI board */
	private ImageIcon xIcon;

	/** represents O as seen on GUI board */
	private ImageIcon oIcon;

	/** represents empty tile as seen on GUI board */
	private ImageIcon emptyIcon;

	/** SuperTicTacToeGame object used in panel */
	private SuperTicTacToeGame game;

	/** shows the game options and win/draw info */
	private JPanel top;

	/** shows the actual game board */
	private JPanel bottom;

	/** size of the board */
	private int boardSize;

	/** x win count label */
	private JLabel xWinLabel;

	/** o win count label */
	private JLabel oWinLabel;

	/** draw total label */
	private JLabel drawLabel;

	/** x win count */
	private JTextArea xWinText;

	/** o win count */
	private JTextArea oWinText;

	/** draw count */
	private JTextArea drawText;

	/** param used to set starting player */
	private int inputPlayer;

	/** button used to undo moves */
	private JButton undoButton;

	/** used to keep track of tile positions */
	private ArrayList<Point> tracker;

	/** represents a game tiles position */
	private Point tile;

	/** used to load a past game */
	private JButton loadButton;

	/** used to save the current game */
	private JButton saveButton;

	/******************************************************************
    Constructor to set up a new panel. Size of the board and starting
    player must be input.
    @param panBoardSize input board size
    @param panPlayer input starting player
	 *****************************************************************/
	public SuperTicTacToePanel(int panBoardSize, int panPlayer) {
		inputPlayer = panPlayer;
		boardSize = panBoardSize;

		//instantiates panels
		top = new JPanel();
		bottom = new JPanel();

		//separates top/bottom panels
		this.add(top, BorderLayout.NORTH);
		this.add(bottom, BorderLayout.SOUTH);

		//instantiates buttons on top panel
		loadButton = new JButton("LOAD");
		top.add(loadButton);
		saveButton = new JButton("SAVE");
		top.add(saveButton);
		undoButton = new JButton("UNDO");
		top.add(undoButton);
		quitButton = new JButton("QUIT");
		top.add(quitButton);

		//instantiates new game
		game = new SuperTicTacToeGame(boardSize, inputPlayer);

		//instantiate win labels and their text areas
		xWinLabel = new JLabel("X: ");
		top.add(xWinLabel);
		xWinText = new JTextArea();
		top.add(xWinText);
		oWinLabel = new JLabel("O: ");
		top.add(oWinLabel);
		oWinText = new JTextArea();
		top.add(oWinText);
		drawLabel = new JLabel("D: ");
		top.add(drawLabel);
		drawText = new JTextArea();
		top.add(drawText);

		//instantiates image icons for x/o/empty
		xIcon = new ImageIcon("x_icon.png");
		oIcon = new ImageIcon("o_icon.png");
		emptyIcon = new ImageIcon("empty_icon.png");

		//sets layout for bottom panel
		bottom.setLayout(new GridLayout(boardSize, boardSize));

		//defines/instantiates button listener
		ButtonListener bListener = new ButtonListener();

		//instantiates tracker/tile
		tracker = new ArrayList<Point>();
		tile = new Point();

		//instantiates board/iBoard
		board = new JButton[boardSize][boardSize];
		iBoard = new Cell[boardSize][boardSize];

		//creates game board as 2d array of buttons, sets their icons
		//as empty, makes the border/margin miniscule, adds the
		//button listener, and adds the button to the panel
		for (int r = 0 ; r < boardSize ; r++) {
			for (int c = 0 ; c < boardSize ; c++) {
				board[r][c] = new JButton(emptyIcon);
				board[r][c].setBorder(null);
				board[r][c].setBorderPainted(true);
				board[r][c].setMargin(new Insets(0, 0, 0, 0));
				board[r][c].addActionListener(bListener);
				bottom.add(board[r][c]);
			}
		}

		//adds button listener to quit and undo
		quitButton.addActionListener(bListener);
		undoButton.addActionListener(bListener);
	}

	/******************************************************************
    ButtonListener used to determine which button on the panel has
    been pressed.
	 *****************************************************************/
	private class ButtonListener implements ActionListener {

		/**************************************************************
	    Used to determine what action should be performed dependent on
	    the button pressed.  
		 *************************************************************/
		public void actionPerformed (ActionEvent e) {

			//if quit button is clicked, exit program
			if (e.getSource() == quitButton)
				System.exit(0);

			//if tile is clicked, changes tile to x or o
			for (int r = 0 ; r < boardSize ; r++)
				for (int c =  0 ; c < boardSize ; c++)
					if (board[r][c] == e.getSource()) {
						game.select(r, c);
						trackPosition(r, c);
					}

			//updates the board display
			displayBoard();

			//window pops up if X wins, keeps track of the win, and
			//asks if the user wants to play again
			if (game.getGameStatus() == GameStatus.X_WON) {
				int xSelect = JOptionPane.showConfirmDialog(null,
						"X WINS! Play again?",
						"X has won the game!",
						JOptionPane.YES_NO_OPTION);
				if (xSelect == JOptionPane.YES_OPTION) {
					game.reset();
					xWinText.setText("" + game.getXWins());
					displayBoard();
				}
				if (xSelect == JOptionPane.NO_OPTION)
					System.exit(0);
			}

			//window pops up if O wins, keeps track of the win, and
			//asks if the user wants to play again
			if (game.getGameStatus() == GameStatus.O_WON) {
				int oSelect = JOptionPane.showConfirmDialog(null, 
						"O WINS! Play again?",
						"O has won the game!",
						JOptionPane.YES_NO_OPTION);
				if (oSelect == JOptionPane.YES_OPTION) {
					game.reset();
					oWinText.setText("" + game.getOWins());
					displayBoard();
				}
				if (oSelect == JOptionPane.NO_OPTION)
					System.exit(0);
			}

			//window pops up if nobody wins, keeps track of the draw,
			//and asks if the user wants to play again
			if (game.getGameStatus() == GameStatus.CATS) {
				int dSelect = JOptionPane.showConfirmDialog(null, 
						"CATS! Play again?",
						"CATS - nobody has won!",
						JOptionPane.YES_NO_OPTION);
				if (dSelect == JOptionPane.YES_OPTION) {
					game.reset();
					drawText.setText("" + game.getDrawTotal());
					displayBoard();
				}
				if (dSelect == JOptionPane.NO_OPTION)
					System.exit(0);
			}

			//if undo button is pressed, the last move is undone
			if (e.getSource() == undoButton) {
				game.setTileEmpty(tracker.get(tracker.size()).getX(), 
						tracker.get(tracker.size()).getY());
				tracker.remove(tracker.size());
				displayBoard();
			}
		}				
	}

	/******************************************************************
    Updates the display of the board by setting the correct icon to the
    tile dependent on what the getBoard() method returns.
	 *****************************************************************/
	private void displayBoard() {
		iBoard = game.getBoard();

		for (int row = 0 ; row < boardSize ; row++) {
			for (int col = 0 ; col < boardSize ; col++) {
				if (iBoard[row][col] == Cell.O)
					board[row][col].setIcon(oIcon);
				if (iBoard[row][col] == Cell.X)
					board[row][col].setIcon(xIcon);
				if (iBoard[row][col] == Cell.EMPTY)
					board[row][col].setIcon(emptyIcon);
			}
		}
	}

	/******************************************************************
    Sets a pointer's coordinates based on input row and column.
    @param row input row of tile
    @param col input column of tile
	 *****************************************************************/
	private void trackPosition(int row, int col) {
		tile.x = row;
		tile.y = col;
		tracker.add(tile);
	}	
}
