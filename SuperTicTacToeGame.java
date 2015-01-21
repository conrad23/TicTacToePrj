/**********************************************************************
The non-visual aspect of the Super Tic-Tac-Toe game.

@author Conner Toney
@version GVSU Fall 2014
 *********************************************************************/
package package1;

public class SuperTicTacToeGame {

	/** the game board */
	private Cell[][] board;

	/** current status of the game */
	private GameStatus status;

	/** used to determine player */
	private int player;

	/** size of board */
	private int boardSize;

	/** total amount of X wins */
	private static int xWins;

	/** total amount of O wins */
	private static int oWins;

	/** total amount of draws */
	private static int drawTotal;

	/** starting player */
	private static int sPlayer;

	/******************************************************************
    Constructor to set up a new game. Board size and starting player
    must be input. All tiles are set to empty and wins/draws are set to
    zero.
    @param pBoardSize input size of board
    @param startPlayer input starting player 
	 *****************************************************************/
	public SuperTicTacToeGame(int pBoardSize, int startPlayer) {
		sPlayer = startPlayer;
		boardSize = pBoardSize;
		status = GameStatus.IN_PROGRESS;
		board = new Cell[boardSize][boardSize];

		for (int r = 0 ; r < boardSize ; r++)
			for (int c = 0 ; c < boardSize ; c++)
				board[r][c] = Cell.EMPTY;
		player = sPlayer;
		xWins = 0;
		oWins = 0;
		drawTotal = 0;
	}

	/******************************************************************
    Sets the tile to X or O depending on the row/col combination input
    and the current player number.
    @param row input row of selected tile
    @param col input column of selected tile
	 *****************************************************************/
	public void select (int row, int col) {
		if (player == 1) {
			board[row][col] = Cell.X;
			player = 2;
		}
		else {
			board[row][col] = Cell.O;
			player = 1;
		}
	}

	/******************************************************************
    Resets the board game back to the beginning, but does not erase
    win/draw totals.
	 *****************************************************************/
	public void reset () {
		status = GameStatus.IN_PROGRESS;
		for (int r = 0 ; r < boardSize ; r++)
			for (int c = 0 ; c < boardSize ; c++)
				board[r][c] = Cell.EMPTY;
		player = sPlayer;
	}

	/******************************************************************
    Sets a tile to empty depending on the input row and column.
    @param row input row of tile to be emptied
    @param col input column of tile to be emptied
	 *****************************************************************/
	public void setTileEmpty(double row, double col) {

		//casting used to convert double to int because of Pointer type
		int r = (int) row;
		int c = (int) col;
		board[r][c] = Cell.EMPTY;
	}

	/******************************************************************
    Determines the current state of the game. IN_PROGRESS is returned
    if there are still empty tiles. X_WON is returned if X has won.
    O_WON is returned if O has won.  CATS is returned if there are no
    empty tiles left and nobody has won.
    @return status current state of game
	 *****************************************************************/
	public GameStatus getGameStatus() {
		int count;

		//checks if there is a winner by row
		for (int r = 0 ; r < boardSize ; r++) {
			count = 0;
			for (int c = 0 ; c < boardSize ; c++) {
				if (board[r][c] == Cell.X)
					count++;
				if (board[r][c] == Cell.O)
					count--;
			}
			if (count == boardSize) {
				status = GameStatus.X_WON;
				xWins++;
				return status;
			}
			if (count == -(boardSize)) {
				status = GameStatus.O_WON;
				oWins++;
				return status;
			}
		}

		//checks if there is a winner by column
		for (int c = 0 ; c < boardSize ; c++) {
			count = 0;
			for (int r = 0 ; r < boardSize ; r++) {
				if (board[r][c] == Cell.X)
					count++;
				if (board[r][c] == Cell.O)
					count--;
			}
			if (count == boardSize) {
				status = GameStatus.X_WON;
				xWins++;
				return status;
			}
			if (count == -(boardSize)) {
				status = GameStatus.O_WON;
				oWins++;
				return status;
			}
		}

		//checks if there is a winner by diagonal starting in top left
		count = 0;
		for (int r = 0 ; r < boardSize ; r++) {
			for (int c = 0 ; c < boardSize ; c++) {
				if (r == c) {
					if (board[r][c] == Cell.X)
						count++;
					if (board[r][c] == Cell.O)
						count--;
				}
				if (count == boardSize) {
					status = GameStatus.X_WON;
					xWins++;
					return status;
				}
				if (count == -(boardSize)) {
					status = GameStatus.O_WON;
					oWins++;
					return status;
				}
			}
		}

		//checks if there is a winner by diagonal starting in top right
		count = 0;
		for (int r = 0 ; r < boardSize ; r++) {
			for (int c = 0 ; c < boardSize ; c++) {
				if (r + c == (boardSize - 1)) {
					if (board[r][c] == Cell.X)
						count++;
					if (board[r][c] == Cell.O)
						count--;
				}
				if (count == boardSize) {
					status = GameStatus.X_WON;
					xWins++;
					return status;
				}
				if (count == -(boardSize)) {
					status = GameStatus.O_WON;
					oWins++;
					return status;
				}
			}
		}

		//checks if the game is a draw, by determining if all the tiles
		//have been played yet
		count = 0;
		for (int r = 0 ; r < boardSize ; r++)
			for (int c = 0 ; c < boardSize ; c++) 
				if (board[r][c] == Cell.X || board[r][c] == Cell.O) 
					count++;
		if (count == (boardSize * boardSize)) {
			status = GameStatus.CATS;
			drawTotal++;
			return status;
		}

		//return IN_PROGRESS if the above if statements dont go through
		return status;
	}

	/******************************************************************
    Returns the entire board.
    @return board the game board
	 *****************************************************************/
	public Cell[][] getBoard() {
		return board;
	}

	/******************************************************************
    Returns the total amount of X wins (corrective equation used)
    @return xWins total number of x wins
	 *****************************************************************/
	public int getXWins() {
		return ((xWins / 2) + 1);
	}

	/******************************************************************
    Returns the total amount of O wins (corrective equation used)
    @return oWins total number of o wins
	 *****************************************************************/
	public int getOWins() {
		return ((oWins / 4) + 1);
	}

	/******************************************************************
    Returns the total amount of draws (corrective equation used)
    @return drawTotal total number of draws
	 *****************************************************************/
	public int getDrawTotal() {
		return (drawTotal / 3);
	}
}
